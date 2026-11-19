package com.boosterltd.iptbank.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ClientTradeDto {
    @Data
    public static class SourceItem {
        private final short irjAmount;
        private final short irrAmount;
    }
    @Data
    public static class TargetItem {
        @NonNull private final String name;
        private final byte amount;
    }

    private final SourceItem sourceItem;
    private final TargetItem targetItem;
}
