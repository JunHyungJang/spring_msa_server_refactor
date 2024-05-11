package org.example.userservice.domain;

import lombok.Data;

@Data
public class RequestUser {
    private String name;
    private String email;
    private String password;
}
