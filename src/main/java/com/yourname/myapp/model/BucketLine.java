package com.yourname.myapp.model;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(name = "bucket_line")
public class BucketLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bucket_id", nullable = false)
    private Bucket bucket;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(nullable = false)
    private Integer quantite;

    // getters, setters, constructeurs
}

