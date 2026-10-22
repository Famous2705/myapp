package com.yourname.myapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @Column(nullable = false, length = 100)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Getter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
