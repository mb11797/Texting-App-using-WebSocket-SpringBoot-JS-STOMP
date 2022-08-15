package com.tom.TextingAppusingWebSocketSpringBootJSSTOMP.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.tom.TextingAppusingWebSocketSpringBootJSSTOMP.model.ChatMessagePOJO;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessagePOJO sendMessage(@Payload ChatMessagePOJO chatMessagePOJO){
        return chatMessagePOJO;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessagePOJO addUser(@Payload ChatMessagePOJO chatMessagePOJO, SimpMessageHeaderAccessor headerAccessor){
        // Add Username in Web Socket Session
        headerAccessor.getSessionAttributes().put("username", chatMessagePOJO.getSender());
        return chatMessagePOJO;
    }
}
