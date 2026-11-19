package com.boosterltd.iptbank.model.trade;

import lombok.Data;

@Data
public class TradeTargetItemPrice {
    private final byte amountInPackage;
    private final byte priceIrjAmount;
    private final short priceIrrAmount;
}
