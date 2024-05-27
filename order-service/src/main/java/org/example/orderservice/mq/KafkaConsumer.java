package org.example.orderservice.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.orderservice.domain.OrderDto;
import org.example.orderservice.service.OrderService;
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

    private OrderService orderService;

    public KafkaConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(groupId = "order-product-rollback-group", topics = "order-product-rollback")
    public void listen1(String kafkaMessage) {

        try {
            // JSON 문자열을 ChatDto 객체로 파싱
            OrderDto orderDto = objectMapper.readValue(kafkaMessage, OrderDto.class);
            String orderId = orderDto.getId();
            orderService.deleteByOrderId(orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}