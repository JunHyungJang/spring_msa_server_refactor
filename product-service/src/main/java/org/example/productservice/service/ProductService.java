package org.example.productservice.service;

import org.example.productservice.domain.ProductDto;

public interface ProductService {

    ProductDto updateProductStock(String id, int qty);

}
