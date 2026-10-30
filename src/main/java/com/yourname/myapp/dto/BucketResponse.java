package com.yourname.myapp.dto;

import com.yourname.myapp.model.Bucket;

import java.math.BigDecimal;
import java.util.List;

public record BucketResponse(
        Long id,
        List<BucketLineResponse> lines,
        BigDecimal total
) {
    public static BucketResponse from(Bucket bucket) {
        List<BucketLineResponse> lines = bucket.getBucketLineList().stream()
                .map(BucketLineResponse::from).toList();
        BigDecimal total = lines.stream()
                .map(BucketLineResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new BucketResponse(bucket.getId(), lines, total);
    }
}
