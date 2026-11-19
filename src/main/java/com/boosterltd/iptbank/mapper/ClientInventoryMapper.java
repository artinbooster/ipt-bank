package com.boosterltd.iptbank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.boosterltd.iptbank.dto.ClientInventoryDto;
import com.boosterltd.iptbank.model.entity.ClientInventoryEntity;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClientInventoryMapper {
    void updateEntityFromDto(
        ClientInventoryDto dto,
        @MappingTarget ClientInventoryEntity entity
    );

    ClientInventoryDto toDto(
        ClientInventoryEntity entity
    );
}
