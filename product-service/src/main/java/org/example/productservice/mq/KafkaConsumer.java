package org.example.productservice.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productservice.domain.RequestProduct;
import org.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private ProductService productService;
    private KafkaProducer kafkaProducer;
    public KafkaConsumer(ProductService productService,KafkaProducer kafkaProducer) {

        this.productService = productService;
        this.kafkaProducer = kafkaProducer;
    }

    @KafkaListener(groupId = "order-product-forward-group", topics = "order-product-forward")
    public void listen(String kafkaMessage) {

        try {
            // JSON 문자열을 ChatDto 객체로 파싱
            RequestProduct requestProduct = objectMapper.readValue(kafkaMessage, RequestProduct.class);
            String productId = requestProduct.getProductId();
            int qty = requestProduct.getQty();
            productService.updateProductStock(productId,qty);
            kafkaProducer.send("order-product-rollback",requestProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}