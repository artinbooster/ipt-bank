package com.boosterltd.iptbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boosterltd.iptbank.model.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {  }
