package com.yourname.myapp.repository;

import com.yourname.myapp.model.Categorie;
import com.yourname.myapp.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findById(Long id) ;
    boolean existsById (Long id) ;
}
