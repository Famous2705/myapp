package com.yourname.myapp.service;

import com.yourname.myapp.model.BucketLine;
import com.yourname.myapp.repository.BucketLineRepository;
import org.springframework.stereotype.Service;

@Service
public class BucketLineService {

    private final BucketLineRepository bucketLineRepository;

    public BucketLineService(BucketLineRepository bucketLineRepository){
        this.bucketLineRepository = bucketLineRepository;
    }


}
