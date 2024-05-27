package org.example.productservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@Builder
public class RequestProduct {
    private String id;
    private String userId;
    private String productId;
    private int unitPrice;
    private int qty;
    private int totalPrice;

}
