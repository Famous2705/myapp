package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "bucket")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String user_id;

    @OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL)
    private List<BucketLine> bucketLineList;

    // getters, setters, constructeurs
}
