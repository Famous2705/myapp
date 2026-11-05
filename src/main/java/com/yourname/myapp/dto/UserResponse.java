package com.yourname.myapp.dto;

import com.yourname.myapp.model.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String passWord
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(), user.getPassWord(),
                user.getEmail()
        );
    }
}
