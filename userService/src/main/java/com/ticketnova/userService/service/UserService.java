package com.ticketnova.userService.service;

import com.ticketnova.userService.dto.UserCreationDto;
import com.ticketnova.userService.dto.UserDto;
import com.ticketnova.userService.entity.User;

import java.util.UUID;

public interface UserService {
    UserCreationDto createUser(UserDto user);
    User updateUser(User user);
    boolean deleteUser(UUID uuid);
    User getUserDetails(UUID uuid);
}
