package com.yourname.myapp.repository;

import com.yourname.myapp.model.BucketLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketLineRepository extends JpaRepository<BucketLine, Long> {
    List<BucketLine> findByBucketId(Long bucketId);

    Optional<BucketLine> findByBucketIdAndProductId(Long bucketId, Long productId);
}
