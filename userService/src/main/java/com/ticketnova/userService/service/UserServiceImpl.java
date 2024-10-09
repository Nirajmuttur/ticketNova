package com.ticketnova.userService.service;

import com.ticketnova.userService.dto.UserCreationDto;
import com.ticketnova.userService.dto.UserDto;
import com.ticketnova.userService.entity.User;
import com.ticketnova.userService.repository.UserRepository;
import com.ticketnova.userService.types.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    KafkaTemplate<String,UserDto> kafkaTemplate;
    private UserCreationDto mapToDto(User user) {
        return UserCreationDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
    @Override
    public UserCreationDto createUser(UserDto user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        Optional<User> existingUserWithEmail = userRepository.findByUsername(user.getUsername());
        if(existingUser.isPresent()){
            throw new RuntimeException("User Already present");
        }
        if(existingUserWithEmail.isPresent()){
            throw new RuntimeException("Username is Already present");
        }
        var newUser = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .username(user.getUsername())
                .build();
        User user1 =userRepository.save(newUser);
        kafkaTemplate.send("user-created",new UserDto(
                user1.getId(),user1.getUsername(), user.getEmail(), user1.getPassword()
        ));
        return mapToDto(user1);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean deleteUser(UUID uuid) {
        return false;
    }

    @Override
    public User getUserDetails(UUID uuid) {
        return null;
    }
}
