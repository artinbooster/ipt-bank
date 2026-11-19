package com.boosterltd.iptbank.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.ClientDto;
import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.ClientEntity;
import com.boosterltd.iptbank.repository.ClientRepository;

import lombok.NonNull;

public interface ClientService {
    public static ClientEntity getEntity(
        @NonNull ClientRepository repository, long id
    ) {
        final ClientEntity entity = repository.findById(id).orElseThrow(
            () -> { return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted("client", "ID", String.valueOf(id))
            ); }
        );
        return entity;
    }
    
    public ClientDto createEntity(@NonNull ClientDto dto);

    public ClientDto getDto(long id);
    public List<ClientDto> getDtoAll();

    public ClientDto updateEntity(long id, @NonNull ClientDto dto);

    public void deleteEntity(long id);
}
