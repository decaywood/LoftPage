/**
 * Created by decaywood on 2015/7/2.
 */
function NetSendManager(gameManager, remoteManager) {

    this.gameManager = gameManager;
    this.remoteManager = remoteManager;
    this.counter = 0;
    var sock= new SockJS('/LoftPage/webSocket');
    this.stompClient = Stomp.over(sock);

}

NetSendManager.prototype.sendGameState = function (keyEvent) {

    var gameState = this.gameManager.serialize();
    var currentNum = this.counter++;
    var expectNum = this.counter;

    var message = {
        userID:this.userID,
        highestScore:gameState.score,
        altKey:keyEvent.altKey,
        ctrlKey:keyEvent.ctrlKey,
        metaKey:keyEvent.metaKey,
        shiftKey:keyEvent.shiftKey,
        which:keyEvent.which,
        currentNum:currentNum,
        expectNum:expectNum
        //randomTiles:randomTiles
    };

    this.sendData('keyDown.do', message);

}

NetSendManager.prototype.connectGame = function () {

    this.userID = Math.uuidCompact();
    var userID = this.userID;

    var stompClient = this.stompClient;
    var gameManager = this.gameManager;
    var sender = this.sendData;

    var callback = function () {

        stompClient.subscribe('/message/responds/' + userID, function(responds){
            alert(responds);
        });

    };
    stompClient.connect("","", callback);

    var success = function (info) {
        smoke.signal(info, function (e) {}, { duration:3000});
        gameManager.restart();
        var tiles = gameManager.getRandomTiles();

        var message = {
            userID:userID,
            gameState:"init",
            randomTiles:JSON.stringify(tiles)
        };
        sender('keyDown.do', message);
    };

    sender('connectGame.do', {
        userID:userID,
        gameState:"connect"
    }, success)


}

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
}