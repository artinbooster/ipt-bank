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

@Entity @Table(name = "client")
@AllArgsConstructor @Data @NoArgsConstructor
public class ClientEntity {
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String gender;
    
    @Column(name = "name_given_name", nullable = false)
    private String givenName;

    @Column(name = "name_surname", nullable = false)
    private String surname;
    
    @Column(name = "name_surname_suffix")
    private String surnameSuffix;
    
    @Column(name = "name_full_name", unique = true, nullable = false)
    private String fullName;
    
    @Column(name = "password_hashed", nullable = false)
    private String hashedPassword;
}
