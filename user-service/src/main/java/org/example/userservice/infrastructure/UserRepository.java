package org.example.userservice.infrastructure;

import org.example.userservice.domain.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findById(String userId);
}

