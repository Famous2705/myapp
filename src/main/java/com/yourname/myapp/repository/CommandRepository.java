package com.yourname.myapp.repository;

import com.yourname.myapp.model.Order;
import com.yourname.myapp.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id) ;
    boolean existsById (Long id) ;
}
