package com.example.marsamaroc.dtos;

import com.example.marsamaroc.dao.entities.Role;

public class RoleDto {
    private Role role;

    public RoleDto(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}