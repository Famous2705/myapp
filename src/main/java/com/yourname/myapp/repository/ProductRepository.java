package com.yourname.myapp.repository;

import com.yourname.myapp.model.Produit;
import jakarta.persistence.LockModeType;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Produit, Long>, JpaSpecificationExecutor<Produit> {
    Optional<Produit> findById(Long id) ;

    boolean existsById (Long id) ;

    Page<Produit> findByCategoryId (Long CategoryId, Pageable pageable);

    Page<Produit> findByNomContainingIgnoreCase(String motCle, Pageable pageable);

    Page<Produit> findByPriceBetween(BigDecimal priceMin, BigDecimal priceMax, Pageable pageable);

    Page<Produit> findByStockGreaterThan(Integer stock, Pageable pageable);

    boolean existsByCategoryId(Long categoryId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Produit p WHERE p.id = :id")
    Optional<Produit> findByIdForUpdate(@Param("id") Long id);
}
