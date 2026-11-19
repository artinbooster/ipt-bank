package com.boosterltd.iptbank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.boosterltd.iptbank.dto.TradeTargetItemDto;
import com.boosterltd.iptbank.mapper.TradeTargetItemMapper;
import com.boosterltd.iptbank.model.entity.TradeTargetItemEntity;
import com.boosterltd.iptbank.repository.TradeTargetItemRepository;
import com.boosterltd.iptbank.service.TradeTargetItemService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeTargetItemServiceImpl implements TradeTargetItemService {
        /* -- Fields -- */
    private final TradeTargetItemMapper mapper;
    private final TradeTargetItemRepository repository;
        /* -- Public Non-static Methods -- */
    @Override
    public TradeTargetItemDto createEntity(@NonNull TradeTargetItemDto dto) {
        TradeTargetItemEntity entity = new TradeTargetItemEntity();
        mapper.updateEntityFromDto(dto, entity);
        TradeTargetItemEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public TradeTargetItemDto getDto(long id) {
        TradeTargetItemEntity entity = TradeTargetItemService.getEntity(repository, id);
        return mapper.toDto(entity);
    }
    @Override
    public List<TradeTargetItemDto> getDtoAll() {
        List<TradeTargetItemEntity> entityList = repository.findAll();
        return (
            entityList.stream().map(
                (TradeTargetItemEntity entity) -> { return mapper.toDto(entity); }
            ).collect(Collectors.toList())
        );
    }

    @Override
    public TradeTargetItemDto updateEntity(long id, @NonNull TradeTargetItemDto dto) {
        TradeTargetItemEntity entity = TradeTargetItemService.getEntity(repository, id);
        mapper.updateEntityFromDto(dto, entity);
        TradeTargetItemEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public void deleteEntity(long id) {
        TradeTargetItemEntity entity = TradeTargetItemService.getEntity(repository, id);
        repository.delete(entity);
    }
}
