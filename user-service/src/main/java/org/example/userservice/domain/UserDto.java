package org.example.userservice.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<ResponseOrder> orderList;

}


