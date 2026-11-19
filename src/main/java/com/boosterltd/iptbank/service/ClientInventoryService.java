package com.boosterltd.iptbank.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.ClientInventoryDto;
import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.ClientInventoryEntity;
import com.boosterltd.iptbank.repository.ClientInventoryRepository;

import lombok.NonNull;

public interface ClientInventoryService {
    public static ClientInventoryEntity getEntity(
        @NonNull ClientInventoryRepository repository, long clientId
    ) {
        final ClientInventoryEntity entity = repository.findById(clientId).orElseThrow(
            () -> { return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted("client inventory", "ID", String.valueOf(clientId))
            ); }
        );
        return entity;
    }

    public ClientInventoryDto getDto(long id);
    public List<ClientInventoryDto> getDtoAll();

    public ClientInventoryDto updateEntity(long id, @NonNull ClientInventoryDto dto);

    public ClientInventoryDto changeNumbers(long id, @NonNull ClientInventoryDto dto);
}
