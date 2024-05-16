package org.example.chatservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDto {
    private String name;
    private int roomId;
    private String content;
}

