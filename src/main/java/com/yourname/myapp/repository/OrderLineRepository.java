package com.yourname.myapp.repository;

import com.yourname.myapp.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    boolean existsByProductId(Long productId);

    List<OrderLine> findByOrderId(Long orderId);
}
