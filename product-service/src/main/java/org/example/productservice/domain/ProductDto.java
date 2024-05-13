package org.example.productservice.domain;

import lombok.Builder;
import lombok.Data;
import org.example.productservice.infrastructure.ProductEntity;


@Data
public class ProductDto {
    private String id;
//    private String name;
    private int price;
    private int stock;

//    public ProductEntity DtoToEntity(ProductDto productDto){
//        ProductEntity productEntity = new ProductEntity();
//
//
//    }
}
