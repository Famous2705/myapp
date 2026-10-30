package com.yourname.myapp.dto;

import com.yourname.myapp.model.OrderLine;

import java.math.BigDecimal;

public record OrderLineResponse(
        Long productId,
        String productNom,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
    public static OrderLineResponse from(OrderLine line) {
        return new OrderLineResponse(
                line.getProduit().getId(),
                line.getProduit().getNom(),
                line.getQuantite(),
                line.getPrixUnitaire(),
                line.getPrixUnitaire().multiply(BigDecimal.valueOf(line.getQuantite())));
    }
}
