package com.boosterltd.iptbank.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.boosterltd.iptbank.dto.ClientDto;
import com.boosterltd.iptbank.model.entity.ClientEntity;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClientMapper {
    @BeanMapping(ignoreUnmappedSourceProperties = { "plainPassword" })
    @Mapping(source = "name.givenName", target = "givenName")
    @Mapping(source = "name.surname", target = "surname")
    @Mapping(source = "name.surnameSuffix", target = "surnameSuffix")
    @Mapping(source = "name.fullName", target = "fullName")
    void updateEntityFromDto(
        ClientDto dto,
        @MappingTarget ClientEntity entity
    );

    @Mapping(ignore = true, target = "plainPassword")
    @InheritInverseConfiguration
    ClientDto toDto(
        ClientEntity entity
    );
}
