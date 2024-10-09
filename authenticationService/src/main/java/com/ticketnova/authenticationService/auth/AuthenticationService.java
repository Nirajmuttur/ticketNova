package com.ticketnova.authenticationService.auth;


import com.ticketnova.authenticationService.User.Role;
import com.ticketnova.authenticationService.User.User;
import com.ticketnova.authenticationService.User.UserDto;
import com.ticketnova.authenticationService.User.UserRepository;
import com.ticketnova.authenticationService.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @KafkaListener(topics = "user-created",groupId = "user-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void register(UserDto userDto) {
        System.out.println("New User"+ userDto);
        var user = User.builder()
                        .id(userDto.getId())
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .build();
        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Map<String, Object> claims = new HashMap<>();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        claims.put("email",user.getEmail());
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        var jwtToken = jwtService.generateToken(claims,user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
