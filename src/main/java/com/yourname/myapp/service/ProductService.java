package com.yourname.myapp.service;

import com.yourname.myapp.dto.ProductRequest;
import com.yourname.myapp.dto.ProductResponse;
import com.yourname.myapp.exception.ResourceNotFoundException;
import com.yourname.myapp.model.Categorie;
import com.yourname.myapp.model.Produit;
import com.yourname.myapp.repository.CategoryRepository;
import com.yourname.myapp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

import javax.naming.InsufficientResourcesException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse create(ProductRequest request) {
        Categorie categorie = categoryRepository.findById(request.CategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Produit produit = new Produit();
        produit.setNom(request.nom());
        produit.setDescription(request.description());
        produit.setPrix(request.price());
        produit.setStock(request.stock());
        produit.setCategorie(categorie);

        return ProductResponse.from(productRepository.save(produit));
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

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Produit produit = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Categorie categorie = categoryRepository.findById(request.CategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        produit.setNom(request.nom());
        produit.setDescription(request.description());
        produit.setPrix(request.price());
        produit.setStock(request.stock());
        produit.setCategorie(categorie);

        return ProductResponse.from(productRepository.save(produit));
    }

    public void delete(Long id) {
        Produit produit = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(produit);
    }

    @Transactional
    public ProductResponse adjustStock(Long id, int newStock) {
        Produit produit = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        produit.setStock(newStock);

        return ProductResponse.from(productRepository.save(produit));
    }

    @Transactional
    void decreaseStock(Long productId, int quantity) throws InsufficientResourcesException {
        Produit produit = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (produit.getStock() < quantity) {
            throw new InsufficientResourcesException("Not enough stock for product " + productId);
        }
        produit.setStock(produit.getStock() - quantity);
    }

    @Transactional
    void restoreStock(Long productId, int quantity) {
        Produit produit = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        produit.setStock(produit.getStock() + quantity);
    }
}
