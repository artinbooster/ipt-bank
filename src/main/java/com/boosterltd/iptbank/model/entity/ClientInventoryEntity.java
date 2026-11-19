package com.boosterltd.iptbank.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "client_inventory")
@AllArgsConstructor @Data @NoArgsConstructor
public class ClientInventoryEntity {
    @Column(name = "id_client", unique = true, nullable = false) @Id
    private long clientId;
    
    @Column(name = "hot_chocolate_amount", nullable = false)
    private byte hotChocolateAmount = 0;

    @Column(name = "series_episode_shekarestan_amount", nullable = false)
    private byte shekarestanSeriesEpisodeAmount = 0;
    
    @Column(name = "series_episode_stillwater_amount", nullable = false)
    private byte stillwaterSeriesEpisodeAmount = 0;

    @Column(name = "vpn_subscription_amount", nullable = false)
    private byte vpnSubscriptionAmount = 0;
}
