package org.example.productservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.productservice.domain.ProductDto;
import org.example.productservice.domain.RequestProduct;
import org.example.productservice.service.ProductService;
import org.springframework.core.env.Environment;
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

    @PutMapping("/product/{productId}")
    public ProductDto updateStock(@PathVariable("productId") String productId, @RequestBody RequestProduct requestProduct)){

        return null;
    }




}
