package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits;

    // getters, setters, constructeurs
}
