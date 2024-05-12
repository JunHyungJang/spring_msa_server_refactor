package org.example.orderservice.domain;

import lombok.Data;
import org.example.orderservice.infrastructure.OrderEntity;

@Data
public class OrderDto {
    private String id;
    private String userId;
    private String productId;
    private int unitPrice;
    private int qty;
    private int totalPrice;

    public OrderEntity DtoToEntity(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderDto.getId());
        orderEntity.setUserId(orderDto.getUserId());
        orderEntity.setProductId(orderDto.getProductId());
        orderEntity.setUnitPrice(orderDto.getUnitPrice());
        orderEntity.setQty(orderDto.getQty());
        orderEntity.setTotalPrice(orderEntity.getTotalPrice());

        return orderEntity;
    }
}
