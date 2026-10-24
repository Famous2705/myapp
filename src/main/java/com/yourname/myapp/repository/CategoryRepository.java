package com.yourname.myapp.repository;

import com.yourname.myapp.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findById(Long id) ;
    boolean existsById (Long id) ;
    Optional<Categorie> findByName(String name);
    boolean existsByName (String name);
}
