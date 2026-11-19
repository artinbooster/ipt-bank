package com.boosterltd.iptbank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.boosterltd.iptbank.dto.ClientWalletDto;
import com.boosterltd.iptbank.mapper.ClientWalletMapper;
import com.boosterltd.iptbank.model.entity.ClientWalletEntity;
import com.boosterltd.iptbank.repository.ClientWalletRepository;
import com.boosterltd.iptbank.service.ClientWalletService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientWalletServiceImpl implements ClientWalletService {
        /* -- Fields -- */
    private final ClientWalletMapper mapper;
    private final ClientWalletRepository repository;
        /* -- Public Non-static Methods -- */
    @Override
    public ClientWalletDto getDto(long id) {
        final ClientWalletEntity entity = ClientWalletService.getEntity(repository, id);
        return mapper.toDto(entity);
    }
    @Override
    public List<ClientWalletDto> getDtoAll() {
        final List<ClientWalletEntity> entityList = repository.findAll();
        return (
            entityList.stream().map(
                (entity) -> { return mapper.toDto(entity); }
            ).collect(Collectors.toList())
        );
    }

    @Override
    public ClientWalletDto updateEntity(long id, @NonNull ClientWalletDto dto) {
        final ClientWalletEntity entity = ClientWalletService.getEntity(repository, id);
        mapper.updateEntityFromDto(dto, entity);
        final ClientWalletEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public ClientWalletDto changeNumbers(long id, @NonNull ClientWalletDto dto) {
        final ClientWalletEntity entity = ClientWalletService.getEntity(repository, id);
        if (dto.getIrjAmount() != null) {
            entity.setIrjAmount(
                (byte) (entity.getIrjAmount() + dto.getIrjAmount())
            );
        }
        if (dto.getIrrAmount() != null) {
            entity.setIrrAmount(
                (byte) (entity.getIrrAmount() + dto.getIrrAmount())
            );
        }
        final ClientWalletEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }
}
