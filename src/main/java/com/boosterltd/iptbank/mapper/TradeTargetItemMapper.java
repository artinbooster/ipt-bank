package com.boosterltd.iptbank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.boosterltd.iptbank.dto.TradeTargetItemDto;
import com.boosterltd.iptbank.model.entity.TradeTargetItemEntity;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TradeTargetItemMapper {
    void updateEntityFromDto(
        TradeTargetItemDto dto,
        @MappingTarget TradeTargetItemEntity entity
    );

    TradeTargetItemDto toDto(
        TradeTargetItemEntity entity
    );
}
