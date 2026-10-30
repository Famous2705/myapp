package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_line")
public class OrderLine {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Order order;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Setter
    @Getter
    @Column(nullable = false)
    private Integer quantite;

    @Setter
    @Getter
    @Column(nullable = false)
    private BigDecimal prixUnitaire;  // prix au moment de l'achat, pas le prix actuel du produit !

    // getters, setters, constructeurs
}
