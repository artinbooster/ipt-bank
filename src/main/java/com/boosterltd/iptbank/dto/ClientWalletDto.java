package com.boosterltd.iptbank.dto;

import lombok.Data;

@Data
public class ClientWalletDto {
    private final Long clientId;
    private final Byte irjAmount;
    private final Short irrAmount;
}
