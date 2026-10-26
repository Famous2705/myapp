package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
public class Categorie {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, unique = true)
    private String nom;

    @Getter
    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits;

    // getters, setters, constructeurs
}
