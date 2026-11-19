package com.boosterltd.iptbank.service.impl;

import static com.boosterltd.lib.security.Security.getHashedVersion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.boosterltd.iptbank.dto.AdminDto;
import com.boosterltd.iptbank.mapper.AdminMapper;
import com.boosterltd.iptbank.model.entity.AdminEntity;
import com.boosterltd.iptbank.repository.AdminRepository;
import com.boosterltd.iptbank.service.AdminService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
        /* -- Fields -- */
    private final AdminMapper mapper;
    private final AdminRepository repository;
        /* -- Public Non-static Methods -- */
    @Override
    public AdminDto createEntity(@NonNull AdminDto dto) {
        final AdminEntity entity = new AdminEntity();
        mapper.updateEntityFromDto(dto, entity);
        entity.setHashedPassword(getHashedVersion(dto.getPlainPassword()));
        final AdminEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public AdminDto getDto(long id) {
        final AdminEntity entity = AdminService.getEntity(repository, id);
        return mapper.toDto(entity);
    }
    @Override
    public List<AdminDto> getDtoAll() {
        final List<AdminEntity> entityList = repository.findAll();
        return (
            entityList.stream().map(
                (entity) -> { return mapper.toDto(entity); }
            ).collect(Collectors.toList())
        );
    }

    @Override
    public AdminDto updateEntity(long id, @NonNull AdminDto dto) {
        final AdminEntity entity = AdminService.getEntity(repository, id);
        if (dto.getPlainPassword() != null) {
            entity.setHashedPassword(getHashedVersion(dto.getPlainPassword()));
        }
        mapper.updateEntityFromDto(dto, entity);
        final AdminEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public void deleteEntity(long id) {
        final AdminEntity entity = AdminService.getEntity(repository, id);
        repository.delete(entity);
    }
}
