package com.boosterltd.iptbank.model;

import com.boosterltd.iptbank.model.InventorySlot.BoundInventorySlot;
import com.boosterltd.iptbank.model.entity.ClientWalletEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @RequiredArgsConstructor
public enum ClientWalletSlot {
    IRJ(
        InventorySlot.ofProvided(
            ClientWalletEntity.class,
            (ClientWalletEntity owner, int newAmount) -> { owner.setIrjAmount((byte) newAmount); },
            (ClientWalletEntity owner) -> { return (int) owner.getIrjAmount(); }
        )
    ),
    IRR(
        InventorySlot.ofProvided(
            ClientWalletEntity.class,
            (ClientWalletEntity owner, int newAmount) -> { owner.setIrrAmount((short) newAmount); },
            (ClientWalletEntity owner) -> { return (int) owner.getIrrAmount(); }
        )
    );

    private final InventorySlot<ClientWalletEntity> inventorySlot;
    @Setter private BoundInventorySlot<ClientWalletEntity> boundInventorySlot;
}
