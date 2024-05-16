package org.example.chatservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.ChatDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.example.chatservice.service.ChatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("/chatting")
public class ChatController {



    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    public void sendMessage(ChatDto chatDto) {
        chatService.sendMessage(chatDto);
    }


}
