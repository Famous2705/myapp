package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "produits")
public class Produit {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String nom;

    @Getter
    @Column(nullable = false)
    private String description;

    @Getter
    @Column(nullable = false)
    private BigDecimal prix;  // jamais de double pour l'argent !

    @Getter
    @Column(nullable = false)
    private Integer stock;

    @Getter
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    // getters, setters, constructeurs
}
