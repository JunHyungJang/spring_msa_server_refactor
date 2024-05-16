package org.example.chatservice.service;

import org.example.chatservice.domain.ChatDto;

public interface ChatService {

//    void test();
    void sendMessage(ChatDto chatDto);
}
