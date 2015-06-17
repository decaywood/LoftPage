package org.decaywood.websocket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * @author: decaywood
 * @date: 2015/6/16 21:44
 */

/**
 * extends : TextWebSocketHandler or BinaryWebSocketHandler
 *
 * Spring’s WebSocket support does not depend on Spring MVC.
 * It is relatively simple to integrate a WebSocketHandler
 * into other HTTP serving environments with the help of WebSocketHttpRequestHandler.
 */
public class ClientWebsocketHandler extends AbstractWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        super.handleTextMessage(session, message);
        String message1 = message.getPayload();
        session.sendMessage(new TextMessage(message1));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }
}
