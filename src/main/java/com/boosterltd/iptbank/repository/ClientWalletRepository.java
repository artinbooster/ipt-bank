package com.boosterltd.iptbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boosterltd.iptbank.model.entity.ClientWalletEntity;

public interface ClientWalletRepository extends JpaRepository<ClientWalletEntity, Long> {  }
