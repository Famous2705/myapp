package com.yourname.myapp.controller;

import com.yourname.myapp.dto.BucketLineResponse;
import com.yourname.myapp.dto.BucketResponse;
import com.yourname.myapp.exception.InsufficientStockException;
import com.yourname.myapp.service.BucketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/bucket")
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BucketResponse> getBucket(@PathVariable Long userId) {
        return ResponseEntity.ok(bucketService.getBucket(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clear(@PathVariable Long userId) {
        bucketService.clear(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<BucketResponse> addProduct(@PathVariable Long userId,
                                                     @RequestParam Long productId,
                                                     @RequestParam int quantity) throws InsufficientStockException {
        return ResponseEntity.ok(bucketService.addProduct(userId, productId, quantity));
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<BucketResponse> updateQuantity(@PathVariable Long userId,
                                                         @PathVariable Long productId,
                                                         @RequestParam int quantity) throws InsufficientStockException {
        return ResponseEntity.ok(bucketService.updateQuantity(userId, productId, quantity));
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<BucketResponse> removeProduct(@PathVariable Long userId,
                                                        @PathVariable Long productId) {
        return ResponseEntity.ok(bucketService.removeProduct(userId, productId));
    }
}
