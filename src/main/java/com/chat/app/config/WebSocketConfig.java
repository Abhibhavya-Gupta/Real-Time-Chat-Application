package com.chat.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:8080") // allow only this endpoint (authentic users)
                .withSockJS(); // fall back for the users which dont have the support to websockets
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //topic/chatroom1 -> jisne bhi is topic channel ko subscribe kara hoga usko hi msg receive hoga
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");


    }
}
