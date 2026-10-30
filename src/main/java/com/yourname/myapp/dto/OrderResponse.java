package com.yourname.myapp.dto;

import com.yourname.myapp.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        LocalDateTime orderDate,
        OrderStatus status,
        List<OrderLineResponse> lines,
        BigDecimal total
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getDateCommande(),
                order.getStatut(),
                order.getLines().stream().map(OrderLineResponse::from).toList(),
                order.getTotal());
    }
}
