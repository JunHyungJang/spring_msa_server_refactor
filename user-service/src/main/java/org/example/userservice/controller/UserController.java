package org.example.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.domain.RequestUser;
import org.example.userservice.domain.UserDto;
import org.example.userservice.service.UserService;
import org.example.userservice.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {
    private UserService userService;
    private Environment env;

    private ModelMapper mapper;
    public UserController(UserService userService,Environment env) {
        this.userService = userService;
        this.env = env;
        this.mapper = new ModelMapper();
    }

    @GetMapping("/health_check")
    public ResponseEntity<String> status(){
        return  ResponseEntity.status(HttpStatus.OK).body(String.format("It's working in user service on port"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtos = userService.getUserByAll();
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserDto> createUser(@RequestBody RequestUser user) {
//        System.out.println("REQUEST"+user.);
        UserDto userDto = mapper.map(user,UserDto.class);
        System.out.println(userDto);
        UserDto newUserDto = userService.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDto);
    }
}
