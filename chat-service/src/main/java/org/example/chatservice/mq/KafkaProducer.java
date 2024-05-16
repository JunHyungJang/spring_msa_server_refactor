package org.example.chatservice.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.domain.ChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, ChatDto chatDto) {
        int roomId = chatDto.getRoomId();
        try {
            String jsonMessage = objectMapper.writeValueAsString(chatDto);
            kafkaTemplate.send(topic,roomId%3,null, jsonMessage);
        } catch(Exception e){
            e.printStackTrace();
        }


    }
}
