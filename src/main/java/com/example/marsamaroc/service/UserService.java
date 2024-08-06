package com.example.marsamaroc.service;



import com.example.marsamaroc.dao.entities.User;
import com.example.marsamaroc.dtos.SignUpDto;
import com.example.marsamaroc.dtos.UserDto;
import com.example.marsamaroc.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found for id: " + id));
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }




    public UserDto findUserDtoByLogin(String login) {
        log.info("Recherche de l'utilisateur avec le login: {}", login);
        Optional<User> userOptional = userRepository.findByLogin(login);
        User user = userOptional.orElseThrow(() -> new RuntimeException("Unknown user"));
        log.info("Utilisateur trouvé: {}", user.getLogin());
        return convertToDto(user);
    }

    public User findByLogin(String login) {
        log.info("Recherche de l'utilisateur avec le login: {}", login);
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Unknown user"));
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), null, user.getRole());
    }

    }
