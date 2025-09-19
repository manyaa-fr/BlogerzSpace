package com.ghanshyam.blogera.dto;

import com.ghanshyam.blogera.user.AppUser;

public class UserDto {
    private String username;

    public UserDto(AppUser user) {
        this.username = user.getUsername();
    }

    // Getter
    public String getUsername() {
        return username;
    }
}
