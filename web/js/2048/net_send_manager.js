/**
 * Created by decaywood on 2015/7/2.
 */

function NetSendManager(gameManager, remoteManager) {

    this.gameManager = gameManager;
    this.remoteManager = remoteManager;
    this.userID = Math.uuidCompact();
    var sock= new SockJS('/LoftPage/webSocket');
    this.stompClient = Stomp.over(sock);

    this.counter = 0;

    this.innerMapper = initInnerMapper();


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
    var innerMapper = this.innerMapper;

    var callback = function () {
        stompClient.subscribe('/message/responds/' + userID, function(responds){

            var tuple = getElement(responds);
            var mapped = map[tuple.keyEvent.which];

            if(tuple.gameState == "init")
                remoteManager.restart(tuple.tiles, tuple.highestScore, tuple.IP);

            if(tuple.gameState == "gaming" && mapped !== undefined) {

                var entry = tuple.tiles.pop();
                entry.move = mapped;
                var tileArr = innerMapper.getTile(tuple.currentNum, entry);

                while(tileArr.length != 0) {
                    var t = tileArr.shift();
                    remoteManager.move(t.move, t, tuple.highestScore);
                }
            }

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

NetSendManager.prototype.initGame = function () {

    var sender = this.sendData();

    this.gameManager.restart();
    var tiles = this.gameManager.getRandomTiles();
    var bestScore = this.gameManager.getBestScore();
    var message = {
        userID:userID,
        gameState:"init",
        highestScore:bestScore,
        randomTiles:JSON.stringify(tiles)
    };

    sender('keyDown.do', message);


};

NetSendManager.prototype.connectGame = function () {
    this.resetState();
    var userID = this.userID;
    var sender = this.sendData;

    var success = function (info) {
        smoke.signal(info, function (e) {}, { duration:3000});
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

NetSendManager.prototype.resetState = function () {

    this.innerMapper.reset();
    this.counter = 0;

};

var getElement = function (jsonFile) {

    var jsonString = JSON.stringify(jsonFile);
    var event = JSON.parse(jsonString).body;
    var body = JSON.parse(event);

    var keyEvent = eventParser(body);
    var tiles = tilesParser(body);
    var gameState = body.gameState;
    var highestScore = body.highestScore;
    var currentNum = body.currentNum;

    return {
        keyEvent:keyEvent,
        tiles:tiles,
        gameState:gameState,
        highestScore:highestScore,
        currentNum:currentNum,
        IP:body.ipaddress
    };

};

var eventParser = function (jsonFile) {
    return {
        altKey:jsonFile.altKey,
        ctrlKey:jsonFile.ctrlKey,
        metaKey:jsonFile.metaKey,
        shiftKey:jsonFile.shiftKey,
        which:jsonFile.which
    };
};

var tilesParser = function (jsonFile) {
    var randomTiles = jsonFile.randomTiles;
    return JSON.parse(randomTiles);
};

var initInnerMapper = function () {
    return {
        mapper:{},
        stackTracer:0,
        add: function (currentNum, tile) {
            this.mapper[currentNum] = tile;
        },
        isValid: function (currentNum) {
            return this.mapper.hasOwnProperty(currentNum);
        },
        getTile: function (currentNum, tile) {

            if(currentNum == this.stackTracer) {
                var array = [tile];
                this.stackTracer++;
                var index = JSON.stringify(this.stackTracer);
                while(this.isValid(index)){
                    array.push(this.mapper[index]);
                    delete this.mapper[index];
                    index = JSON.stringify(this.stackTracer++);
                }
                return array;
            } else {
                this.mapper[currentNum] = tile;
                return [];
            }
        },
        reset: function () {
            this.mapper = {};
            this.stackTracer = 0;
        }
    };
};

