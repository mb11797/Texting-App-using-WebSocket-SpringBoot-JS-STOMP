package com.tom.TextingAppusingWebSocketSpringBootJSSTOMP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.tom.TextingAppusingWebSocketSpringBootJSSTOMP.model.ChatMessagePOJO;

@Component
public class WebSocketEventListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
        LOGGER.info("Received a new Web Socket Connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        String userName = (String)headerAccessor.getSessionAttributes().get("username");
        if(userName != null){
            LOGGER.info("User Disconnected: " + userName);
            
            ChatMessagePOJO chatMessagePOJO = new ChatMessagePOJO();
            chatMessagePOJO.setType(ChatMessagePOJO.MessageType.LEAVE);
            chatMessagePOJO.setSender(userName);

            messagingTemplate.convertAndSend("/topic/public", chatMessagePOJO);
        }
    }

}
