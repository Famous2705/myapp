package com.yourname.myapp.dto;

import com.yourname.myapp.model.BucketLine;

import java.math.BigDecimal;

public record BucketLineResponse(
        Long productId,
        String productNom,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal subtotal
) {
    public static BucketLineResponse from(BucketLine line) {
        BigDecimal price = line.getProduit().getPrix();
        return new BucketLineResponse(
                line.getProduit().getId(),
                line.getProduit().getNom(),
                price,
                line.getQuantite(),
                price.multiply(BigDecimal.valueOf(line.getQuantite())));
    }
}
