package org.example.userservice.service;

import org.example.userservice.domain.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(String id);
    List<UserDto> getUserByAll();

}
