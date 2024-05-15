package org.example.chatservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.mq.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService{

    private final KafkaProducer kafkaProducer;

    @Autowired
    public ChatServiceImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void test() {
        kafkaProducer.send("chat","test");
    }
}
