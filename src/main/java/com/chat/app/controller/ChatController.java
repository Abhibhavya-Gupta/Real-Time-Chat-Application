package com.chat.app.controller;

import com.chat.app.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage SendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    // Serve chat.html on both root (/) and (/chat)
    @GetMapping({"/", "/chat"})
    public String chat() {
        return "chat";
    }
}
