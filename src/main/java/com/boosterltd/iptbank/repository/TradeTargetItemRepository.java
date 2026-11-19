package com.boosterltd.iptbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boosterltd.iptbank.model.entity.TradeTargetItemEntity;

public interface TradeTargetItemRepository extends JpaRepository<TradeTargetItemEntity, Long> { 
    public Optional<TradeTargetItemEntity> findByName(String name);
}
