package org.example.userservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestUser {
    private String name;
    private String email;
    private String password;
}
