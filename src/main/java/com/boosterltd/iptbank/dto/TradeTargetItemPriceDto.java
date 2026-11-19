package com.boosterltd.iptbank.dto;

import lombok.Data;

@Data
public class TradeTargetItemPriceDto {
    private final Long id;
    private final Long tradeTargetItemId;
    private final Byte amountInPackage;
    private final Byte priceIrjAmount;
    private final Short priceIrrAmount;
}
