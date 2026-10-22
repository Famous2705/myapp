package com.yourname.myapp.dto;

import com.yourname.myapp.model.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(), user.getName(),
                user.getEmail(), user.getCreatedAt()
        );
    }
}
