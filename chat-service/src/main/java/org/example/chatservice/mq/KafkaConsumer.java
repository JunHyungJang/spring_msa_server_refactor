package org.example.chatservice.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "chat", groupId = "chat-group")
    public void listen(String kafkaMessage) {
        System.out.println(kafkaMessage);
    }
}
