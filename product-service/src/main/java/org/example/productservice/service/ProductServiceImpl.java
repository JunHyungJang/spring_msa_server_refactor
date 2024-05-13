package org.example.productservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.productservice.domain.ProductDto;
import org.example.productservice.infrastructure.ProductEntity;
import org.example.productservice.infrastructure.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto updateProductStock(String id,int qty) {
        ProductEntity productEntity = productRepository.findById(id);
        System.out.println(productEntity);
        int remain = productEntity.getStock() - qty;
        if (remain <0){
            throw new RuntimeException();
        }
//        return null;
        productEntity.setStock(remain);
        ProductDto newProductDto = productEntity.EntityToDto(productEntity);
        productRepository.save(productEntity);

        return newProductDto;

    }

}
