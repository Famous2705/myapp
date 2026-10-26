package com.yourname.myapp.service;

import com.yourname.myapp.dto.ProductRequest;
import com.yourname.myapp.dto.ProductResponse;
import com.yourname.myapp.exception.ResourceNotFoundException;
import com.yourname.myapp.model.Produit;
import com.yourname.myapp.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductResponse create(ProductRequest request) {
        return new ProductResponse(
                request.Id(),
                request.nom(),
                request.description(),
                request.price(),
                request.stock(),
                request.CategoryId(),
                request.CategoryNom()
        );
    }

    public ProductResponse getById(Long id){
        return productRepository.findById(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Page<ProductResponse> search(Long categoryId, String keyword,
                                        BigDecimal minPrice, BigDecimal maxPrice,
                                        boolean availableOnly, Pageable pageable) {
        Specification<Produit> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(categoryId != null) {
                predicates.add(cb.equal(root.get("categorie").get("id"), categoryId));
            }

            if (keyword != null && !keyword.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nom")),
                        "%" + keyword.toLowerCase() + "%"));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("prix"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("prix"), maxPrice));
            }
            if (availableOnly) {
                predicates.add(cb.greaterThan(root.get("stock"), 0));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productRepository.findAll(spec, pageable)
                .map(ProductResponse::from);
    }
}
