/**
 * Created by decaywood on 2015/7/2.
 */
function NetSendManager(gameManager) {

    this.gameManager = gameManager;

    var sock= new SockJS('/LoftPage/webSocket');
    this.stompClient = Stomp.over(sock);

}

NetSendManager.prototype.sendGameState = function () {

    var gameState = this.gameManager.serialize();

    var message = {
        grid:        gameState.grid,
        score:       gameState.score,
        over:        gameState.over,
        won:         gameState.won,
        keepPlaying: gameState.keepPlaying,
        userID: this.randomStr
    }

    $.ajax({
        url:'keyDown.do',
        data:message,
        async:true,
        cache:false,
        type:'POST',
        dataType:'json'
    });

}

NetSendManager.prototype.connectGame = function () {

    this.randomStr = Math.uuidCompact();

    $.ajax({
        url:'connectGame.do',
        data:{
            userID:this.randomStr
        },
        async:true,
        cache:false,
        type:'POST',
        dataType:'json',
        success: function (info) {

            if("Waiting For Connection!" == info || "Connect Game Success!" == info) {
                self.restart.call(self, {});
            }
            smoke.signal(info, function (e) {}, { duration:3000});
        }
    });

    var callback = function (frame) {

        this.stompClient.subscribe('/message/responds/' + randomStr, function(responds){
            alert(responds);
        });

    };

    this.stompClient.connect("","", callback);
}