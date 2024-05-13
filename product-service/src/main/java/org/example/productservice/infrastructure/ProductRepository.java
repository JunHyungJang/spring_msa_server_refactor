package org.example.productservice.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    ProductEntity findById(String id);
}
