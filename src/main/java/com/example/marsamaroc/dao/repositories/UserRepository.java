package com.example.marsamaroc.dao.repositories;

import com.example.marsamaroc.dao.entities.User;
import com.example.marsamaroc.dtos.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    Optional<User> findUserById(Long id);


}