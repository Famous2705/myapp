package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bucket")
public class Bucket {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Getter
    @Setter
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User utilisateur;

    @Setter
    @Getter
    @OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BucketLine> bucketLineList = new ArrayList<>();

    // getters, setters, constructeurs
}
