package com.chat.app.controller;

import com.chat.app.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") // The messages are mapped with the 'sendMessage' endpoint.
    @SendTo("/topic/messages") // The message will be sent to these subscribers who have subscribed to the topic/messages
    public ChatMessage SendMessage(ChatMessage chatMessage)
    {
        return chatMessage;
    }

    @GetMapping("/chat")
    public  String Chat()
    {
        return "chat";
    }

    @GetMapping
    public String sayHello()
    {
      return "hello";
    }

}
