package com.yourname.myapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User utilisateur;

    @Column(nullable = false)
    private LocalDateTime dateCommande;

    @Enumerated(EnumType.STRING)
    private OrderStatus statut;  // EN_ATTENTE, PAYEE, EXPEDIEE, ANNULEE

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<OrderLine> lines;

    @Column(nullable = false)
    private BigDecimal total;

    // getters, setters, constructeurs
}
