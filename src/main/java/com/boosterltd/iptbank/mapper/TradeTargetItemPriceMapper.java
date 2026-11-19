package com.boosterltd.iptbank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.boosterltd.iptbank.dto.TradeTargetItemPriceDto;
import com.boosterltd.iptbank.model.entity.TradeTargetItemPriceEntity;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TradeTargetItemPriceMapper {
    void updateEntityFromDto(
        TradeTargetItemPriceDto dto,
        @MappingTarget TradeTargetItemPriceEntity entity
    );

    TradeTargetItemPriceDto toDto(
        TradeTargetItemPriceEntity entity
    );
}
