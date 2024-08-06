package com.example.marsamaroc.dtos;

import com.example.marsamaroc.dao.entities.Role;
import com.example.marsamaroc.dao.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String token;
    private Role role;

    public UserDto(User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.role = role; // Set role as Role object
    }

}