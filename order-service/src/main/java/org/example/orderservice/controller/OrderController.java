package org.example.orderservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.example.orderservice.domain.OrderDto;
import org.example.orderservice.domain.RequestOrder;
import org.example.orderservice.mq.KafkaProducer;
import org.example.orderservice.service.OrderService;
import org.springframework.core.env.Environment;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class OrderController {

    private OrderService orderService;
    private Environment env;
    private ModelMapper mapper = new ModelMapper();

    private KafkaProducer kafkaProducer;

    public OrderController(OrderService orderService, Environment env, KafkaProducer kafkaProducer) {
        this.orderService = orderService;
        this.env = env;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/health_check")
    public ResponseEntity<String> status(){
        return  ResponseEntity.status(HttpStatus.OK).body(String.format("It's working in user service on port"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrder(){
        List<OrderDto> orderDtos = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@PathVariable("userId") String userId){
        List<OrderDto> orderDtos = orderService.getAllOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);

    }
    //Saga pattern
    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody RequestOrder order){
//        OrderDto orderDto = mapper.map(order, OrderDto.class);
        OrderDto orderDto = order.RequestToDto(order);
        OrderDto newOrderDto = orderService.createOrder(orderDto);
        kafkaProducer.send("order-product-forward",newOrderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderDto);
    }


}
