package com.yourname.myapp.dto;

import com.yourname.myapp.model.Categorie;

public record CategoryResponse(
        Long id,
        String nom
) {
    public static CategoryResponse from(Categorie c) {
        return new CategoryResponse(c.getId(), c.getNom());
    }
}
