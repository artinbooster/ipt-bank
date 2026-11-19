package com.boosterltd.iptbank.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "client_wallet")
@AllArgsConstructor @Data @NoArgsConstructor
public class ClientWalletEntity {
    @Column(name = "id_client", unique = true, nullable = false) @Id
    private long clientId;

    @Column(nullable = false)
    private byte irjAmount = 0;

    @Column(nullable = false)
    private short irrAmount = 0;
}
