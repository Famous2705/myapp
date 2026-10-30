package com.yourname.myapp.service;

import com.yourname.myapp.dto.BucketResponse;
import com.yourname.myapp.repository.BucketRepository;
import org.springframework.stereotype.Service;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;

    public BucketService(BucketRepository bucketRepository){
        this.bucketRepository = bucketRepository;
    }

    BucketResponse getBucket(Long userId)
    BucketResponse addProduct(Long userId, Long productId, int quantity)
    BucketResponse updateQuantity(Long userId, Long productId, int quantity)
    BucketResponse removeProduct(Long userId, Long productId)
    void clear(Long userId)
}
