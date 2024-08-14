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
        this.id = user.getId(); // Correctly set the ID
        this.firstName = user.getFirstName(); // Correctly set the first name
        this.lastName = user.getLastName(); // Correctly set the last name
        this.login = user.getLogin(); // Correctly set the login
        this.password = user.getPassword(); // Correctly set the password
        this.role = user.getRole(); // Correctly set the role
    }

    public UserDto(Role role) {
        this.role = role;

    }
}