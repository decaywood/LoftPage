package org.decaywood.websocket;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @author: decaywood
 * @date: 2015/6/17 13:53
 */

@Component
public class ClientHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        logger.debug("============= Before Handshake ============");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        logger.debug("============= After Handshake =============");
        super.afterHandshake(request, response, wsHandler, ex);


    }

}
