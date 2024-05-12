package org.example.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.domain.UserDto;
import org.example.userservice.infrastructure.UserEntity;
import org.example.userservice.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(String.valueOf(UUID.randomUUID()));
        UserEntity userEntity = mapper.map(userDto,UserEntity.class);
        UserEntity newUserEntity = userRepository.save(userEntity);
        UserDto newUserDto = mapper.map(newUserEntity,UserDto.class);
        return newUserDto;
    }

    @Override
    public UserDto getUserById(String id) {
        UserEntity userEntity = userRepository.findById(id);
        UserDto userDto = mapper.map(userEntity,UserDto.class);
        return userDto;
    }

    @Override
    public List<UserDto> getUserByAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = userEntities.stream()
                .map(userEntity -> mapper.map(userEntity,UserDto.class))
                .collect(Collectors.toList());
        return userDtos;
    }
}
