package com.boosterltd.iptbank.service.impl;

import static com.boosterltd.lib.security.Security.getHashedVersion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boosterltd.iptbank.dto.ClientDto;
import com.boosterltd.iptbank.mapper.ClientMapper;
import com.boosterltd.iptbank.model.entity.ClientEntity;
import com.boosterltd.iptbank.model.entity.ClientInventoryEntity;
import com.boosterltd.iptbank.model.entity.ClientWalletEntity;
import com.boosterltd.iptbank.repository.ClientInventoryRepository;
import com.boosterltd.iptbank.repository.ClientRepository;
import com.boosterltd.iptbank.repository.ClientWalletRepository;
import com.boosterltd.iptbank.service.ClientInventoryService;
import com.boosterltd.iptbank.service.ClientService;
import com.boosterltd.iptbank.service.ClientWalletService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
        /* -- Fields -- */
    private final ClientMapper mapper;
    private final ClientRepository repository;
    private final ClientWalletRepository walletRepository;
    private final ClientInventoryRepository inventoryRepository;
        /* -- Public Non-static Methods -- */
    @Override @Transactional
    public ClientDto createEntity(@NonNull ClientDto dto) {
        final ClientEntity entity = new ClientEntity();
        mapper.updateEntityFromDto(dto, entity);
        if (dto.getPlainPassword() != null) {
            entity.setHashedPassword(getHashedVersion(dto.getPlainPassword()));
        }
        final ClientEntity savedEntity = repository.save(entity);
        
        final ClientWalletEntity walletEntity = new ClientWalletEntity(
            savedEntity.getId(), (byte) 0, (byte) 0
        );
        walletRepository.save(walletEntity);

        final ClientInventoryEntity inventoryEntity = new ClientInventoryEntity(
            savedEntity.getId(),
            (byte) 0, (byte) 0, (byte) 0, (byte) 0
        );
        inventoryRepository.save(inventoryEntity);
        
        return mapper.toDto(savedEntity);
    }

    @Override
    public ClientDto getDto(long id) {
        final ClientEntity entity = ClientService.getEntity(repository, id);
        return mapper.toDto(entity);
    }
    @Override
    public List<ClientDto> getDtoAll() {
        final List<ClientEntity> entityList = repository.findAll();
        return (
            entityList.stream().map(
                (entity) -> { return mapper.toDto(entity); }
            ).collect(Collectors.toList())
        );
    }

    @Override
    public ClientDto updateEntity(long id, @NonNull ClientDto dto) {
        final ClientEntity entity = ClientService.getEntity(repository, id);
        if (dto.getPlainPassword() != null) {
            entity.setHashedPassword(getHashedVersion(dto.getPlainPassword()));
        }
        mapper.updateEntityFromDto(dto, entity);
        final ClientEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override @Transactional
    public void deleteEntity(long id) {
        final ClientInventoryEntity inventoryEntity = ClientInventoryService.getEntity(inventoryRepository, id);
        final ClientWalletEntity walletEntity = ClientWalletService.getEntity(walletRepository, id);
        final ClientEntity entity = ClientService.getEntity(repository, id);
        inventoryRepository.delete(inventoryEntity);
        walletRepository.delete(walletEntity);
        repository.delete(entity);
    }
}
