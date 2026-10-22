package com.yourname.myapp.repository;

import com.yourname.myapp.model.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Optional<Bucket> findById(Long id) ;
    boolean existsById (Long id) ;
}
