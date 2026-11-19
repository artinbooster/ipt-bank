package com.boosterltd.iptbank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.boosterltd.iptbank.dto.TradeTargetItemPriceDto;
import com.boosterltd.iptbank.mapper.TradeTargetItemPriceMapper;
import com.boosterltd.iptbank.model.entity.TradeTargetItemPriceEntity;
import com.boosterltd.iptbank.repository.TradeTargetItemPriceRepository;
import com.boosterltd.iptbank.service.TradeTargetItemPriceService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeTargetItemPriceServiceImpl implements TradeTargetItemPriceService {
        /* -- Fields -- */
    private final TradeTargetItemPriceMapper mapper;
    private final TradeTargetItemPriceRepository repository;
        /* -- Public Non-static Methods -- */
    @Override
    public TradeTargetItemPriceDto createEntity(@NonNull TradeTargetItemPriceDto dto) {
        TradeTargetItemPriceEntity entity = new TradeTargetItemPriceEntity();
        mapper.updateEntityFromDto(dto, entity);
        TradeTargetItemPriceEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public TradeTargetItemPriceDto getDto(long id) {
        TradeTargetItemPriceEntity entity = TradeTargetItemPriceService.getEntity(repository, id);
        return mapper.toDto(entity);
    }
    @Override
    public List<TradeTargetItemPriceDto> getDtoAll() {
        List<TradeTargetItemPriceEntity> entityList = repository.findAll();
        return (
            entityList.stream().map(
                (entity) -> { return mapper.toDto(entity); }
            ).collect(Collectors.toList())
        );
    }

    @Override
    public TradeTargetItemPriceDto updateEntity(long id, @NonNull TradeTargetItemPriceDto dto) {
        TradeTargetItemPriceEntity entity = TradeTargetItemPriceService.getEntity(repository, id);
        mapper.updateEntityFromDto(dto, entity);
        TradeTargetItemPriceEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public void deleteEntity(long id) {
        TradeTargetItemPriceEntity entity = TradeTargetItemPriceService.getEntity(repository, id);
        repository.delete(entity);
    }

    @Override
    public TradeTargetItemPriceDto changeNumbers(long id, @NonNull TradeTargetItemPriceDto dto) {
        TradeTargetItemPriceEntity entity = TradeTargetItemPriceService.getEntity(repository, id);
        if (dto.getAmountInPackage() != null) {
            entity.setAmountInPackage(
                (byte) (entity.getAmountInPackage() + dto.getAmountInPackage())
            );
        }
        if (dto.getPriceIrjAmount() != null) {
            entity.setPriceIrjAmount(
                (byte) (entity.getPriceIrjAmount() + dto.getPriceIrjAmount())
            );
        }
        if (dto.getPriceIrrAmount() != null) {
            entity.setPriceIrrAmount(
                (byte) (entity.getPriceIrrAmount() + dto.getPriceIrrAmount())
            );
        }
        TradeTargetItemPriceEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }
}
