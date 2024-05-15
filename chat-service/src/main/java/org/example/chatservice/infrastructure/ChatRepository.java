package org.example.chatservice.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatEntity,String> {
}
