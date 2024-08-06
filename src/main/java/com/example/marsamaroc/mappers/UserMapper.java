package com.example.marsamaroc.mappers;

import com.example.marsamaroc.dao.entities.User;
import com.example.marsamaroc.dtos.SignUpDto;
import com.example.marsamaroc.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);


    UserDto userToUserDto(User user);

    // Vous pouvez également avoir une méthode pour convertir UserDto en User si nécessaire
    User userDtoToUser(UserDto userDto);


}
