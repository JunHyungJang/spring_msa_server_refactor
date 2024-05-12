package org.example.orderservice.service;

import org.example.orderservice.domain.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> getAllOrders();
    List<OrderDto> getAllOrdersByUserId(String userId);


}
