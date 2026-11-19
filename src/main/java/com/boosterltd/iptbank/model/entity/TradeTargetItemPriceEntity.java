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

@Entity @Table(name = "trade_target_item_price")
@AllArgsConstructor @Data @NoArgsConstructor
public class TradeTargetItemPriceEntity {
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private Long id;
    
    @Column(name = "id_trade_target_item", nullable = false)
    private Long tradeTargetItemId;
    
    @Column(nullable = false)
    private byte amountInPackage;
    
    @Column(nullable = false)
    private byte priceIrjAmount;

    @Column(nullable = false)
    private short priceIrrAmount;
}
