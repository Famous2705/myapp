package com.yourname.myapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private BigDecimal prixUnitaire;  // prix au moment de l'achat, pas le prix actuel du produit !

    // getters, setters, constructeurs
}
