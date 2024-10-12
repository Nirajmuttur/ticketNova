package com.ticketnova.userService.controller;

import com.ticketnova.userService.dto.UserCreationDto;
import com.ticketnova.userService.dto.UserDto;
import com.ticketnova.userService.service.UserService;
import com.ticketnova.userService.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Response<UserCreationDto>> register(@RequestBody UserDto user){
        UserCreationDto newUser = userService.createUser(user);
        Response<UserCreationDto> response = Response.successfulResponse(HttpStatus.OK.value(), "User Created Successfully",newUser);
        return ResponseEntity.ok(response);
    }
}
