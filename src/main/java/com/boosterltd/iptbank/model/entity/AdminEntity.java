package com.boosterltd.iptbank.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "admin")
@AllArgsConstructor @Data @NoArgsConstructor
public class AdminEntity {
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;

    @Column(name = "password_hashed", nullable = false)
    private String hashedPassword;

    @Column(unique = true, nullable = false)
    private String username;
}
