package org.example.chatservice.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
@Getter
@Setter
public class ChatEntity {
    @Id
    private String id;
    private String name;
    private int roomId;
    private String content;
}
