package com.boosterltd.iptbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boosterltd.iptbank.model.entity.ClientInventoryEntity;

public interface ClientInventoryRepository extends JpaRepository<ClientInventoryEntity, Long> {  }
