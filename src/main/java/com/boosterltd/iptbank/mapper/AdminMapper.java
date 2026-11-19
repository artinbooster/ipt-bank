package com.boosterltd.iptbank.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.boosterltd.iptbank.dto.AdminDto;
import com.boosterltd.iptbank.model.entity.AdminEntity;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AdminMapper {
    @BeanMapping(ignoreUnmappedSourceProperties = { "plainPassword" })
    void updateEntityFromDto(
        AdminDto dto,
        @MappingTarget AdminEntity entity
    );

    @Mapping(ignore = true, target = "plainPassword")
    @InheritInverseConfiguration
    AdminDto toDto(
        AdminEntity entity
    );
}
