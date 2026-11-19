package com.boosterltd.iptbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boosterltd.iptbank.model.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    public Optional<AdminEntity> findByUsername(String username);
}
