package com.yourname.myapp.service;

import com.yourname.myapp.dto.BucketResponse;
import com.yourname.myapp.exception.ResourceNotFoundException;
import com.yourname.myapp.model.Bucket;
import com.yourname.myapp.model.BucketLine;
import com.yourname.myapp.model.Produit;
import com.yourname.myapp.model.User;
import com.yourname.myapp.repository.BucketRepository;
import com.yourname.myapp.repository.ProductRepository;
import com.yourname.myapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public BucketService(
            BucketRepository bucketRepository,
            ProductRepository productRepository,
            UserRepository userRepository, ProductRepository productRepository1, UserRepository userRepository1){
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository1;
        this.userRepository = userRepository1;
    }

    BucketResponse getBucket(Long userId) {
        return bucketRepository.findByUtilisateurId(userId)
                .map(BucketResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Bucket not found"));
    }

    @Transactional
    BucketResponse addProduct(Long userId, Long productId, int quantity) throws InsufficientResourcesException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Produit produit = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Bucket bucket = getOrCreateBucket(userId);

        BucketLine line = bucket.getBucketLineList().stream()
                .filter(l -> l.getProduit().getId().equals(productId))
                .findFirst()
                .orElse(null);

        int newQuantity = (line == null ? 0 : line.getQuantite()) + quantity;
        if (newQuantity > produit.getStock()) {
            throw new InsufficientResourcesException("Not enough stock for product " + productId);
        }

        if (line == null) {
            line = new BucketLine();
            line.setBucket(bucket);
            line.setProduit(produit);
            bucket.getBucketLineList().add(line);
        }
        line.setQuantite(newQuantity);

        return BucketResponse.from(bucketRepository.save(bucket));
    }

    private Bucket getOrCreateBucket(Long userId) {
        return bucketRepository.findByUtilisateurId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                    Bucket bucket = new Bucket();
                    bucket.setUser_id(userId);
                    return bucketRepository.save(bucket);
                });
    }

    @Transactional
    BucketResponse updateQuantity(Long userId, Long productId, int quantity) throws InsufficientResourcesException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Bucket bucket = getOrCreateBucket(userId);
        BucketLine line = bucket.getBucketLineList().stream()
                .filter(l -> l.getProduit().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not in bucket"));

        if (quantity > line.getProduit().getStock()) {
            throw new InsufficientResourcesException("Not enough stock for product " + productId);
        }
        line.setQuantite(quantity);
        return BucketResponse.from(bucket);
    }

    @Transactional
    BucketResponse removeProduct(Long userId, Long productId) {
        Bucket bucket = getOrCreateBucket(userId);
        boolean removed = bucket.getBucketLineList()
                .removeIf(l -> l.getProduit().getId().equals(productId));
        if (!removed) {
            throw new ResourceNotFoundException("Product not in bucket");
        }
        return BucketResponse.from(bucket);
    }

    @Transactional
    void clear(Long userId) {
        bucketRepository.findByUtilisateurId(userId)
                .ifPresent(bucket -> bucket.getBucketLineList().clear());
    }
}
