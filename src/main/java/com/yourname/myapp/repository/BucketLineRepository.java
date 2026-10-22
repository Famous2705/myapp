package com.yourname.myapp.repository;

import com.yourname.myapp.model.BucketLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketLineRepository extends JpaRepository<BucketLine, Long> {
    Optional<BucketLine> findById(Long id) ;
    boolean existsById (Long id) ;
}
