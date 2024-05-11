package org.example.userservice.domain;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String password;
}


