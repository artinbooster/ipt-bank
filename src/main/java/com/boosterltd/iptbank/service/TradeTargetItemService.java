package com.boosterltd.iptbank.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.TradeTargetItemDto;
import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.TradeTargetItemEntity;
import com.boosterltd.iptbank.repository.TradeTargetItemRepository;

import lombok.NonNull;

public interface TradeTargetItemService {
    public static TradeTargetItemEntity getEntity(
        @NonNull TradeTargetItemRepository repository, long id
    ) {
        TradeTargetItemEntity entity = repository.findById(id).orElseThrow(
            () -> { return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted("trade target item", "ID", String.valueOf(id))
            ); }
        );
        return entity;
    }
    
    public TradeTargetItemDto createEntity(@NonNull TradeTargetItemDto dto);

    public TradeTargetItemDto getDto(long id);
    public List<TradeTargetItemDto> getDtoAll();

    public TradeTargetItemDto updateEntity(long id, @NonNull TradeTargetItemDto dto);

    public void deleteEntity(long id);
}
