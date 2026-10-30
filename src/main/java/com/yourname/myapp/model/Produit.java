package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produits")
public class Produit {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String nom;

    @Setter
    @Getter
    @Column(nullable = false)
    private String description;

    @Setter
    @Getter
    @Column(nullable = false)
    private BigDecimal prix;  // jamais de double pour l'argent !

    @Setter
    @Getter
    @Column(nullable = false)
    private Integer stock;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    // getters, setters, constructeurs
}
