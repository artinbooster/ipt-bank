package com.boosterltd.iptbank.dto;

import lombok.Data;

@Data
public class ClientInventoryDto {
    private final Long clientId;
    private final Byte hotChocolateAmount;
    private final Byte shekarestanSeriesEpisodeAmount;
    private final Byte stillwaterSeriesEpisodeAmount;
    private final Byte vpnSubscriptionAmount;
}
