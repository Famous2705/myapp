package com.yourname.myapp.repository;

import com.yourname.myapp.model.Order;
import com.yourname.myapp.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommandRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    // Sécurité : une commande n'est retrouvée que si elle appartient bien à cet utilisateur
    Optional<Order> findByIdAndUtilisateurId(Long id, Long utilisateurId);

    // Vue admin
    Page<Order> findByStatut(OrderStatus statut, Pageable pageable);
}
