/**
 * Created by decaywood on 2015/7/2.
 */

function NetSendManager(gameManager, remoteManager) {

    this.gameManager = gameManager;
    this.remoteManager = remoteManager;
    this.counter = 0;
    this.userID = Math.uuidCompact();
    var sock= new SockJS('/LoftPage/webSocket');
    this.stompClient = Stomp.over(sock);

    this.map = {
        38: 0, // Up
        39: 1, // Right
        40: 2, // Down
        37: 3, // Left
        75: 0, // Vim up
        76: 1, // Vim right
        74: 2, // Vim down
        72: 3, // Vim left
        87: 0, // W
        68: 1, // D
        83: 2, // S
        65: 3  // A
    };
    this.initStomp();
}



NetSendManager.prototype.initStomp = function () {
    var stompClient = this.stompClient;
    var userID = this.userID;
    var map = this.map;
    var remoteManager = this.remoteManager;
    var callback = function () {
        stompClient.subscribe('/message/responds/' + userID, function(responds){

            var tuple = getElement(responds);
            var mapped = map[tuple.keyEvent.which];
            if(tuple.gameState == "init")
                remoteManager.restart(tuple.tiles, tuple.highestScore);
            if(tuple.gameState == "gaming" && mapped !== undefined)
                remoteManager.move(mapped, tuple.tiles.pop(), tuple.highestScore);

        });
    };
    var errorCallback = function (e) {};
    stompClient.connect("","", callback,errorCallback);
};

NetSendManager.prototype.sendGameState = function (keyEvent) {

    var tiles = this.gameManager.getRandomTiles();
    var bestScore = this.gameManager.getBestScore();

    var which = keyEvent.which;
    var map = this.map;
    var mapped = map[which];

    if(mapped !== undefined) {
        var currentNum = this.counter++;
        var expectNum = this.counter;
    }

    var message = {
        userID:this.userID,
        gameState:"gaming",
        highestScore:bestScore,
        altKey:keyEvent.altKey,
        ctrlKey:keyEvent.ctrlKey,
        metaKey:keyEvent.metaKey,
        shiftKey:keyEvent.shiftKey,
        which:keyEvent.which,
        currentNum:currentNum,
        expectNum:expectNum,
        randomTiles:JSON.stringify(tiles)
    };

    this.sendData('keyDown.do', message);

};

NetSendManager.prototype.connectGame = function () {

    var userID = this.userID;
    var gameManager = this.gameManager;
    var sender = this.sendData;

    var success = function (info) {
        smoke.signal(info, function (e) {}, { duration:3000});
        gameManager.restart();
        var tiles = gameManager.getRandomTiles();
        var bestScore = gameManager.getBestScore();
        var message = {
            userID:userID,
            gameState:"init",
            highestScore:bestScore,
            randomTiles:JSON.stringify(tiles)
        };

        sender('keyDown.do', message);
    };

    sender('connectGame.do', {
        userID:userID,
        gameState:"connect"
    }, success)

};

NetSendManager.prototype.sendData = function (target, message, success) {
    $.ajax({
        url:target,
        data:message,
        async:true,
        cache:false,
        type:'POST',
        dataType:'json',
        success:success
    });
};

var getElement = function (jsonFile) {
    var jsonString = JSON.stringify(jsonFile);
    var event = JSON.parse(jsonString).body;
    var body = JSON.parse(event);

    var keyEvent = eventParser(body);
    var tiles = tilesParser(body);
    var gameState = body.gameState;
    var highestScore = body.highestScore;
    var tuple = {
        keyEvent:keyEvent,
        tiles:tiles,
        gameState:gameState,
        highestScore:highestScore
    };

    return tuple;
};

var eventParser = function (jsonFile) {
    var keyEvent = {
        altKey:jsonFile.altKey,
        ctrlKey:jsonFile.ctrlKey,
        metaKey:jsonFile.metaKey,
        shiftKey:jsonFile.shiftKey,
        which:jsonFile.which
    };
    return keyEvent;
};

var tilesParser = function (jsonFile) {
    var randomTiles = jsonFile.randomTiles;
    var tiles = JSON.parse(randomTiles);
    return tiles;
};