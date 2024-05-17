package org.example.userservice.domain;

import com.jun.models.OrderObject;
import lombok.Data;

@Data
public class ResponseOrder {
    private String id;
    private String userId;
    private String productId;
    private int unitPrice;
    private int qty;
    private int totalPrice;


}
