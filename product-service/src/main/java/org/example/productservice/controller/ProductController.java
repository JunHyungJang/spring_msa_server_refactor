package org.example.productservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.example.productservice.domain.ProductDto;
import org.example.productservice.domain.RequestProduct;
import org.example.productservice.service.ProductService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class ProductController {

    private ProductService productService;
    private Environment env;

    public ProductController(ProductService productService,Environment env) {
        this.productService = productService;
        this.env = env;
//        this.mapper = new ModelMapper();
    }

    @GetMapping("/health_check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body(String.format("It's working in user service on port"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")));
    }
    @PutMapping("/product")
    public ResponseEntity<ProductDto> updateStock(@RequestBody RequestProduct requestProduct){
        ProductDto productDto = productService.updateProductStock(requestProduct.getId(), requestProduct.getQty());
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }




}
