package org.example.orderservice.service;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.domain.OrderDto;
import org.example.orderservice.infrastructure.OrderEntity;
import org.example.orderservice.infrastructure.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private final ModelMapper mapper = new ModelMapper();

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        OrderEntity orderEntity = orderDto.DtoToEntity(orderDto);
        OrderEntity newOrderEntity = orderRepository.save(orderEntity);

        OrderDto newOrderDto = orderEntity.EntityToDto(newOrderEntity);
        return newOrderDto;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderDto> orderDtos = orderEntities.stream()
                .map(orderEntity ->orderEntity.EntityToDto(orderEntity))
                .toList();
        return orderDtos;
    }

    @Override
    public List<OrderDto> getAllOrdersByUserId(String userId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByUserId(userId);
        List<OrderDto> orderDtos = orderEntities.stream()
                .map(orderEntity -> orderEntity.EntityToDto(orderEntity))
                .toList();
        return orderDtos;
    }
}
