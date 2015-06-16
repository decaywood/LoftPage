package org.decaywood.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author: decaywood
 * @date: 2015/6/16 21:44
 */

/**
 *  extends : TextWebSocketHandler or BinaryWebSocketHandler
 */
public class ClientWebsocketHanler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        super.handleTextMessage(session, message);
    }
}
