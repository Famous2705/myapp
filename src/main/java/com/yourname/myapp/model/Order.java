package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User utilisateur;

    @Setter
    @Getter
    @Column(nullable = false)
    private LocalDateTime dateCommande;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private OrderStatus statut;  // EN_ATTENTE, PAYEE, EXPEDIEE, ANNULEE

    @Setter
    @Getter
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<OrderLine> lines;

    @Setter
    @Getter
    @Column(nullable = false)
    private BigDecimal total;

    // getters, setters, constructeurs
}
