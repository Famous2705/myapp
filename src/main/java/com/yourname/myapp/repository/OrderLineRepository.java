package com.yourname.myapp.repository;

import com.yourname.myapp.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    Optional<OrderLine> findById(Long id) ;
    boolean existsById (Long id) ;
}
