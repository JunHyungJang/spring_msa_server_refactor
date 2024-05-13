package org.example.productservice.infrastructure;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.productservice.domain.ProductDto;

@Data
@Entity
@Table(name = "products")
//@AllArgsConstructor
public class ProductEntity {
    @Id
    private String id;
    private int price;
    private int stock;

    public ProductDto EntityToDto(ProductEntity productEntity){
        ProductDto productDto = new ProductDto();
        productDto.setId(productEntity.getId());
        productDto.setStock(productEntity.getStock());
        productDto.setPrice(productEntity.getPrice());

        return productDto;
    }
}


