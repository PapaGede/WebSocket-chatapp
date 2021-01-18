package com.turntabl.io.controller;

import com.turntabl.io.model.ChatMessage;
import com.turntabl.io.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @Autowired
    SimpMessageSendingOperations sendingOperations;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    @EventListener
    public void handleWebSocketConnectListener (final SessionConnectedEvent event){
        logger.info("Connected ");
    }

    @EventListener
    public void handleWebSocketDisconnectListener (final SessionDisconnectEvent event){
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        final String username = (String) headerAccessor.getSessionAttributes().get("username");
        final ChatMessage chatMessage = ChatMessage.builder()
                .type(MessageType.DISCONNECT)
                .sender(username)
                .build();

        sendingOperations.convertAndSend("/topic/public",chatMessage);
    }
}
