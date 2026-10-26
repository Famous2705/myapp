package com.yourname.myapp.dto;

import com.yourname.myapp.model.Produit;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String nom,
        String description,
        BigDecimal price,
        Integer stock,
        Long categoryId,
        String categoryNom
) {
    public static ProductResponse from(Produit produit) {
        return new ProductResponse(
                produit.getId(), produit.getNom(),
                produit.getDescription(), produit.getPrix(),
                produit.getStock(),
                produit.getCategorie().getId(),
                produit.getCategorie().getNom()
        );
    }
}
