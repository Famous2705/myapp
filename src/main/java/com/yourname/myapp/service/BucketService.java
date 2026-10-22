package com.yourname.myapp.service;

import com.yourname.myapp.repository.BucketRepository;
import org.springframework.stereotype.Service;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;

    public BucketService(BucketRepository bucketRepository){
        this.bucketRepository = bucketRepository;
    }
}
