package org.example.userservice.infrastructure;

import org.example.userservice.domain.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findById(String userId);
}
