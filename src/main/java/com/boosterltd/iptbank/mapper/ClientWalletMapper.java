package com.boosterltd.iptbank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.boosterltd.iptbank.dto.ClientWalletDto;
import com.boosterltd.iptbank.model.entity.ClientWalletEntity;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClientWalletMapper {
    void updateEntityFromDto(
        ClientWalletDto dto,
        @MappingTarget ClientWalletEntity entity
    );

    ClientWalletDto toDto(
        ClientWalletEntity entity
    );
}
