package org.example.orderservice.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RequestOrder {
    private String userId;
    private String productId;
    private int unitPrice;
    private int qty;

    public OrderDto RequestToDto(RequestOrder requestOrder){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(String.valueOf(UUID.randomUUID()));
        orderDto.setUserId(requestOrder.getUserId());
        orderDto.setProductId(requestOrder.getProductId());
        orderDto.setUnitPrice(requestOrder.getUnitPrice());
        orderDto.setQty(requestOrder.getQty());
        orderDto.setTotalPrice(requestOrder.getUnitPrice() * requestOrder.getQty());
        return orderDto;
    }
}
