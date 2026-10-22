package com.yourname.myapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal prix;  // jamais de double pour l'argent !

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    // getters, setters, constructeurs
}
