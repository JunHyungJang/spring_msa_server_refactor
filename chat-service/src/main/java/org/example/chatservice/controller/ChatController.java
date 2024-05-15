package org.example.chatservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.example.chatservice.service.ChatService;

@Controller
@Slf4j
public class ChatController {



    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


}
