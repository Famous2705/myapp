package com.yourname.myapp.service;

import com.yourname.myapp.dto.OrderResponse;
import com.yourname.myapp.exception.ResourceNotFoundException;
import com.yourname.myapp.model.*;
import com.yourname.myapp.repository.BucketRepository;
import com.yourname.myapp.repository.CommandRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Service
public class OrderService {

    private final CommandRepository commandRepository;
    private final BucketRepository bucketRepository;
    private final ProductService productService;

    public OrderService(
            CommandRepository commandRepository,
            BucketRepository bucketRepository,
            ProductService productService){
        this.commandRepository = commandRepository;
        this.bucketRepository = bucketRepository;
        this.productService = productService;
    }

    @Transactional
    public OrderResponse placeOrder(Long userId) throws InsufficientResourcesException {
        Bucket bucket = bucketRepository.findByUtilisateurId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Bucket not found"));

        if (bucket.getBucketLineList().isEmpty()) {
            throw new IllegalStateException("Bucket is empty");
        }

        Order order = new Order();
        order.setUtilisateur(bucket.getUtilisateur());
        order.setStatut(OrderStatus.WAITING);
        order.setDateCommande(LocalDateTime.now());

        List<BucketLine> bucketLines = bucket.getBucketLineList().stream()
                .sorted(Comparator.comparing(l -> l.getProduit().getId()))
                .toList();

        BigDecimal total = BigDecimal.ZERO;
        for (BucketLine bucketLine : bucketLines) {
            Produit produit = bucketLine.getProduit();
            int quantity = bucketLine.getQuantite();

            productService.decreaseStock(produit.getId(), quantity);

            OrderLine orderLine = new OrderLine();
            orderLine.setOrder(order);
            orderLine.setProduit(produit);
            orderLine.setQuantite(quantity);
            orderLine.setPrixUnitaire(produit.getPrix());
            order.getLines().add(orderLine);

            total = total.add(produit.getPrix().multiply(BigDecimal.valueOf(quantity)));
        }
        order.setTotal(total);

        Order saved = commandRepository.save(order);
        bucket.getBucketLineList().clear();

        return OrderResponse.from(saved);
    }

    public OrderResponse getOrder(Long orderId, Long userId) {
        return commandRepository.findByIdAndUtilisateurId(userId, orderId)
                .map(OrderResponse :: from)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }


    public Page<OrderResponse> getHistory(Long userId, Pageable pageable) {
        Specification<Order> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(userId != null) {
                predicates.add(cb.equal(root.get("order").get("id"), userId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return commandRepository.findAll(spec, pageable)
                .map(OrderResponse::from);
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId, Long userId) {
        Order order = commandRepository.findByIdAndUtilisateurId(userId, orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        commandRepository.delete(order);

        return OrderResponse.from(order);
    }

    public Page<OrderResponse> getAllOrders(OrderStatus status, Pageable pageable) {
        Specification<Order> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return commandRepository.findAll(spec, pageable)
                .map(OrderResponse::from);
    }
    // admin

    @Transactional
    public OrderResponse changeStatus(Long orderId, OrderStatus newStatus) {
        Order order = commandRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatut(newStatus);

        return OrderResponse.from(commandRepository.save(order));
    }          // admin
}
