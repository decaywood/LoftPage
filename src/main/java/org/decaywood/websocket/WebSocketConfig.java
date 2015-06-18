package org.decaywood.websocket;

import org.decaywood.controller.GameController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/6/18.
 */

@Configuration
@EnableWebSocketMessageBroker
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {


    @Resource
    private HandshakeHandler handshakeHandler;
    @Resource
    private ClientHandshakeInterceptor interceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/message");
        config.setApplicationDestinationPrefixes("/game");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/webSocket")
                .setHandshakeHandler(handshakeHandler)
                .addInterceptors(interceptor)
                .withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(500 * 1024);
        registration.setSendBufferSizeLimit(1024 * 1024);
        registration.setSendTimeLimit(600000);
    }

    @Bean
    public GameController GameController() {
        return new GameController();
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler();
    }

    @Bean
    public ClientHandshakeInterceptor handshakeInterceptor() {
        return new ClientHandshakeInterceptor();
    }

}
