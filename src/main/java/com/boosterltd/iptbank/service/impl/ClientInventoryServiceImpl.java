package com.boosterltd.iptbank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.boosterltd.iptbank.dto.ClientInventoryDto;
import com.boosterltd.iptbank.mapper.ClientInventoryMapper;
import com.boosterltd.iptbank.model.entity.ClientInventoryEntity;
import com.boosterltd.iptbank.repository.ClientInventoryRepository;
import com.boosterltd.iptbank.service.ClientInventoryService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientInventoryServiceImpl implements ClientInventoryService {
        /* -- Fields -- */
    private final ClientInventoryMapper mapper;
    private final ClientInventoryRepository repository;
        /* -- Public Non-static Methods -- */
    @Override
    public ClientInventoryDto getDto(long id) {
        final ClientInventoryEntity entity = ClientInventoryService.getEntity(repository, id);
        return mapper.toDto(entity);
    }
    @Override
    public List<ClientInventoryDto> getDtoAll() {
        final List<ClientInventoryEntity> entityList = repository.findAll();
        return (
            entityList.stream().map(
                (entity) -> { return mapper.toDto(entity); }
            ).collect(Collectors.toList())
        );
    }

    @Override
    public ClientInventoryDto updateEntity(long id, @NonNull ClientInventoryDto dto) {
        final ClientInventoryEntity entity = ClientInventoryService.getEntity(repository, id);
        mapper.updateEntityFromDto(dto, entity);
        final ClientInventoryEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public ClientInventoryDto changeNumbers(long id, @NonNull ClientInventoryDto dto) {
        final ClientInventoryEntity entity = ClientInventoryService.getEntity(repository, id);
        if (dto.getHotChocolateAmount() != null) {
            entity.setHotChocolateAmount(
                (byte) (entity.getHotChocolateAmount() + dto.getHotChocolateAmount())
            );
        }
        if (dto.getShekarestanSeriesEpisodeAmount() != null) {
            entity.setShekarestanSeriesEpisodeAmount(
                (byte) (entity.getShekarestanSeriesEpisodeAmount() + dto.getShekarestanSeriesEpisodeAmount())
            );
        }
        if (dto.getStillwaterSeriesEpisodeAmount() != null) {
            entity.setStillwaterSeriesEpisodeAmount(
                (byte) (entity.getStillwaterSeriesEpisodeAmount() + dto.getStillwaterSeriesEpisodeAmount())
            );
        }
        if (dto.getVpnSubscriptionAmount() != null) {
            entity.setVpnSubscriptionAmount(
                (byte) (entity.getVpnSubscriptionAmount() + dto.getVpnSubscriptionAmount())
            );
        }
        final ClientInventoryEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }
}
