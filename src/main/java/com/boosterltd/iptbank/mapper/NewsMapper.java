package com.boosterltd.iptbank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.boosterltd.iptbank.dto.NewsDto;
import com.boosterltd.iptbank.model.entity.NewsEntity;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NewsMapper {
    void updateEntityFromDto(
        NewsDto dto,
        @MappingTarget NewsEntity entity
    );

    NewsDto toDto(
        NewsEntity entity
    );
}
