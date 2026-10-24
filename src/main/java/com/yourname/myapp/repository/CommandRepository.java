package com.yourname.myapp.repository;

import com.yourname.myapp.model.Order;
import com.yourname.myapp.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandRepository extends JpaRepository<Order, Long> {

    // Historique d'un utilisateur, la plus récente en premier
    Page<Order> findByUserIdOrderByDateOrderDesc(Long userId, Pageable pageable);

    // Sécurité : une commande n'est retrouvée que si elle appartient bien à cet utilisateur
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    // Vue admin
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
}
