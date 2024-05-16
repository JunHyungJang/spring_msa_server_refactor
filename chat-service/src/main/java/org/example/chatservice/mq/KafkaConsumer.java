package org.example.chatservice.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chatservice.domain.ChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @KafkaListener(groupId = "chat-group", topicPartitions = @TopicPartition(topic = "chat", partitions = {"0"}))
    public void listen1(String kafkaMessage) {

        try {
            // JSON 문자열을 ChatDto 객체로 파싱
            ChatDto chatDto = objectMapper.readValue(kafkaMessage, ChatDto.class);
            int roomId = chatDto.getRoomId();
            simpMessagingTemplate.convertAndSend("/sub/chatroom/" + roomId, chatDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Partition 0" + kafkaMessage);

    }
    @KafkaListener(groupId = "chat-group", topicPartitions = @TopicPartition(topic = "chat", partitions = {"1"}))
    public void listen2(String kafkaMessage) {
        try {
            // JSON 문자열을 ChatDto 객체로 파싱
            ChatDto chatDto = objectMapper.readValue(kafkaMessage, ChatDto.class);
            int roomId = chatDto.getRoomId();
            simpMessagingTemplate.convertAndSend("/sub/chatroom/" + roomId, chatDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Partition 1: "+kafkaMessage);

    } @KafkaListener(groupId = "chat-group", topicPartitions = @TopicPartition(topic = "chat", partitions = {"2"}))
    public void listen3(String kafkaMessage) {
        try {
            // JSON 문자열을 ChatDto 객체로 파싱
            ChatDto chatDto = objectMapper.readValue(kafkaMessage, ChatDto.class);
            int roomId = chatDto.getRoomId();
            simpMessagingTemplate.convertAndSend("/sub/chatroom/" + roomId, chatDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Partition 2: "+kafkaMessage);


    }
}
