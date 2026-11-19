package com.boosterltd.iptbank.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.ClientWalletDto;
import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.ClientWalletEntity;
import com.boosterltd.iptbank.repository.ClientWalletRepository;

import lombok.NonNull;

public interface ClientWalletService {
    public static ClientWalletEntity getEntity(
        @NonNull ClientWalletRepository repository, long clientId
    ) {
        final ClientWalletEntity entity = repository.findById(clientId).orElseThrow(
            () -> { return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted("client wallet", "ID", String.valueOf(clientId))
            ); }
        );
        return entity;
    }

    public ClientWalletDto getDto(long id);
    public List<ClientWalletDto> getDtoAll();

    public ClientWalletDto updateEntity(long id, @NonNull ClientWalletDto dto);

    public ClientWalletDto changeNumbers(long id, @NonNull ClientWalletDto dto);
}
