package com.turntabl.io.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

@Builder
public class ChatMessage {
    @Getter
    private MessageType type;
    @Getter
    private String content;
    @Getter
    private String sender;
    @Getter
    private String time;


}
