package com.yourname.myapp.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank String nom
) {
}
