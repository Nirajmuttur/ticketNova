package com.ticketnova.userService.service;

import com.ticketnova.userService.dto.UserCreationDto;
import com.ticketnova.userService.dto.UserDto;
import com.ticketnova.userService.entity.User;
import com.ticketnova.userService.repository.UserRepository;
import com.ticketnova.userService.types.Role;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
//        kafkaTemplate.send("user-created",new UserDto(
//                user1.getId(),user1.getUsername(), user.getEmail(), user1.getPassword()
//        ));
        sendUserCreatedEvent(new UserDto(
                user1.getId(),user1.getUsername(), user.getEmail(), user1.getPassword()
        ));
        return mapToDto(user1);
    }

    public void sendUserCreatedEvent(UserDto userDto) {
        try {
            CompletableFuture<SendResult<String, UserDto>> future = kafkaTemplate.send("user-created",new UserDto(
                    userDto.getId(),userDto.getUsername(), userDto.getEmail(), userDto.getPassword()
            ));
            future.thenAccept(result-> {
                RecordMetadata metadata = result.getRecordMetadata();
                System.out.println("Sent message=[" + userDto + "] with offset=[" + metadata.offset() + "]");
            }).exceptionally(ex -> {
                System.out.println("Unable to send message=[" + userDto + "] due to : " + ex.getMessage());
                return null;
            });
        } catch (Exception e) {
            System.out.println("Kafka is unavailable. Failed to send user event: " + e.getMessage());
            // Optionally: Log or store the event for later processing
        }
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
