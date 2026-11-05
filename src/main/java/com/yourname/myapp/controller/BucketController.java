package com.yourname.myapp.controller;

import com.yourname.myapp.dto.BucketLineResponse;
import com.yourname.myapp.dto.BucketResponse;
import com.yourname.myapp.service.BucketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;

@RestController
@RequestMapping("/api/v1/bucket")
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {this.bucketService = bucketService;}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clear(@PathVariable Long id) {
        bucketService.clear(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BucketResponse> getBucket(@PathVariable Long id) {
        return ResponseEntity.ok(bucketService.getBucket(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BucketResponse> addProduct (@PathVariable Long id,
                                                      Long productId,
                                                      int quantity) throws InsufficientResourcesException {
        return ResponseEntity.ok(bucketService.addProduct(id, productId, quantity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BucketResponse> updateQuantity(@PathVariable Long id,
                                                          Long productId,
                                                          int quantity) throws InsufficientResourcesException {
        return ResponseEntity.ok(bucketService.updateQuantity(id, productId, quantity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BucketResponse> removeProduct(@PathVariable Long id,
                                                        Long productId) {
        return ResponseEntity.ok(bucketService.removeProduct(id, productId));
    }
}
