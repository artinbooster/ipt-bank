package com.boosterltd.iptbank.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.TradeTargetItemPriceDto;
import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.TradeTargetItemPriceEntity;
import com.boosterltd.iptbank.repository.TradeTargetItemPriceRepository;

import lombok.NonNull;

public interface TradeTargetItemPriceService {
    public static TradeTargetItemPriceEntity getEntity(
        @NonNull TradeTargetItemPriceRepository repository, long id
    ) {
        TradeTargetItemPriceEntity entity = repository.findById(id).orElseThrow(
            () -> { return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted("trade target item", "ID", String.valueOf(id))
            ); }
        );
        return entity;
    }

    public TradeTargetItemPriceDto createEntity(@NonNull TradeTargetItemPriceDto dto);

    public TradeTargetItemPriceDto getDto(long id);
    public List<TradeTargetItemPriceDto> getDtoAll();

    public TradeTargetItemPriceDto updateEntity(long id, @NonNull TradeTargetItemPriceDto dto);

    public void deleteEntity(long id);
    
    public TradeTargetItemPriceDto changeNumbers(long id, @NonNull TradeTargetItemPriceDto dto);
}
