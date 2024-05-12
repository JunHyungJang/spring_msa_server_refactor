package org.example.orderservice.infrastructure;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.example.orderservice.domain.OrderDto;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {
    @Id
    private String id;
    private String userId;
    private String productId;
    private int unitPrice;
    private int qty;
    private int totalPrice;

    public OrderDto EntityToDto(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderEntity.getId());
        orderDto.setUserId(orderEntity.getUserId());
        orderDto.setProductId(orderEntity.getProductId());
        orderDto.setUnitPrice(orderEntity.getUnitPrice());
        orderDto.setQty(orderEntity.getQty());
        orderDto.setTotalPrice(orderDto.getTotalPrice());

        return orderDto;
    }
}
