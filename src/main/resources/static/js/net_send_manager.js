/**
 * Created by decaywood on 2015/7/2.
 */

function NetSendManager(gameManager, remoteManager) {

    this.msg = $('#msg');
    this.gameManager = gameManager;
    this.remoteManager = remoteManager;
    this.userID = Math.uuidCompact();
    var sock= new SockJS('/webSocket');
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

    var that = this;

    var callback = function () {
        console.info("in call back")
        that.stompClient.subscribe('/message/responds/' + that.userID, function(responds){

            var tuple = getElement(responds);
            var mapped = that.map[tuple.keyEvent.which];

            console.info(tuple.gameState);
            if(tuple.gameState == "game_connect_ack") {
                that.initGame(that.sendData, that.gameManager, that.userID);
            }

            else if(tuple.gameState == "game_init")
                that.remoteManager.restart(tuple.tiles, tuple.highestScore, tuple.IP);

            else if(tuple.gameState == "gaming" && mapped !== undefined) {

                var entry = tuple.tiles.pop();
                entry.move = mapped;
                var tileArr = that.innerMapper.getTile(tuple.currentNum, entry);

                while(tileArr.length != 0) {
                    var t = tileArr.shift();
                    that.remoteManager.move(t.move, t, tuple.highestScore);
                }
            }

            else if (tuple.gameState == "game_disconnect") {
                that.sendMsg("Game Reconnect");
                that.gameManager.clearGame();
                that.remoteManager.clearGame();
                that.resetState();
            }

        });
    };
    var errorCallback = function (e) {
        console.info(e);
    };
    this.stompClient.connect("","", callback,errorCallback);
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
        altKey:keyEvent.altKey ? keyEvent.altKey : "",
        ctrlKey:keyEvent.ctrlKey ? keyEvent.ctrlKey : "",
        metaKey:keyEvent.metaKey ? keyEvent.metaKey : "",
        shiftKey:keyEvent.shiftKey ? keyEvent.shiftKey : "",
        which:keyEvent.which ? keyEvent.which : "",
        currentNum:currentNum,
        expectNum:expectNum,
        randomTiles:JSON.stringify(tiles)
    };

    this.sendData('keyDown', message);

};

NetSendManager.prototype.initGame = function (sender, gameManager, userID) {

    gameManager.restart();
    var tiles = gameManager.getRandomTiles();
    var bestScore = gameManager.getBestScore();


    var message = {
        userID:userID,
        gameState:"game_init",
        highestScore:bestScore,
        randomTiles:JSON.stringify(tiles)
    };

    sender('keyDown', message);


};

NetSendManager.prototype.sendMsg = function (info) {
    var msg = this.msg;
    msg.text(info);
    setTimeout(function () {msg.text(' ');},3000)
};

NetSendManager.prototype.connectGame = function () {
    this.resetState();
    var that = this;
    var sender = this.sendData;
    var success = function (info) {
        that.sendMsg(info);
    };
    sender('connectGame', {
        userID:that.userID,
        gameState:"game_connect"
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
        success:success,
        error: function (e) {
            if(e.responseText) {
                success(e.responseText); // non-Json solution
            }
        }
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

