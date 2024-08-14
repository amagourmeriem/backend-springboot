package com.example.marsamaroc.service;

import com.example.marsamaroc.dao.entities.User;
import com.example.marsamaroc.dtos.RoleDto;
import com.example.marsamaroc.dtos.SignUpDto;
import com.example.marsamaroc.dtos.UserDto;
import com.example.marsamaroc.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.marsamaroc.dao.repositories.UserRepository;
import com.example.marsamaroc.mappers.UserMapper;
import com.example.marsamaroc.dtos.CredentialsDto;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserDto login(CredentialsDto credentialsDto) {
        log.info("Attempting to log in with login: {}", credentialsDto.getLogin());

        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> {
                    log.error("User not found with login: {}", credentialsDto.getLogin());
                    return new AppException("Unknown user", HttpStatus.NOT_FOUND);
                });

        log.info("User found: {}", user);

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            log.info("Password matches for user: {}", user.getLogin());
            return userMapper.toUserDto(user);
        }

        log.error("Invalid password for user: {}", credentialsDto.getLogin());
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public User findById(Long id) {
        log.info("Attempting to find user with ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new IllegalArgumentException("User not found for id: " + id);
                });
    }

    public UserDto register(SignUpDto userDto) {
        log.info("Attempting to register user with login: {}", userDto.getLogin());

        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());
        if (optionalUser.isPresent()) {
            log.error("Login already exists: {}", userDto.getLogin());
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findUserDtoByLogin(String login) {
        log.info("Searching for user with login: {}", login);

        Optional<User> userOptional = userRepository.findByLogin(login);
        User user = userOptional.orElseThrow(() -> {
            log.error("User not found with login: {}", login);
            return new RuntimeException("Unknown user");
        });

        log.info("User found: {}", user);
        return convertToDto(user);
    }

    public User getUserByLogin(String login) {
        log.info("Searching for user with login: {}", login);

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> {
                    log.error("User not found with login: {}", login);
                    return new RuntimeException("User not found");
                });

        log.info("User found: {}", user);
        return user;
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), null, user.getRole());
    }

    public RoleDto getUserRole(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        // Vérifiez si l'utilisateur est présent
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new RoleDto(user.getRole());
        } else {
            throw new UsernameNotFoundException("User not found with login hihi: " + login);
        }
    }

    public UserDto getRoleByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + login));
        return new UserDto(user.getRole());
    }
}
