package com.example.marsamaroc.controllers;

import com.example.marsamaroc.config.UserAuthenticationProvider;
import com.example.marsamaroc.dtos.CredentialsDto;
import com.example.marsamaroc.dtos.SignUpDto;
import com.example.marsamaroc.dtos.UserDto;
import com.example.marsamaroc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getLogin()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }


    @GetMapping("/find/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        System.out.println("Appel à getUserByLogin avec le login: " + login);
        UserDto userDto = userService.findUserDtoByLogin(login);
        return ResponseEntity.ok(userDto);
    }

}