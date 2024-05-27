package org.example.orderservice.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    List<OrderEntity> findAllByUserId(String userId);
    void deleteById(String orderId);

    Optional<OrderEntity> findById(String orderId);
}
