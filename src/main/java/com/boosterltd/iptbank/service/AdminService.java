package com.boosterltd.iptbank.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.AdminDto;
import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.AdminEntity;
import com.boosterltd.iptbank.repository.AdminRepository;

import lombok.NonNull;

public interface AdminService {
    public static AdminEntity getEntity(@NonNull AdminRepository repository, long id) {
        final AdminEntity entity = repository.findById(id).orElseThrow(
            () -> { return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted("admin", "ID", String.valueOf(id))
            ); }
        );
        return entity;
    }
    
    public AdminDto createEntity(@NonNull AdminDto dto);

    public AdminDto getDto(long id);
    public List<AdminDto> getDtoAll();

    public AdminDto updateEntity(long id, @NonNull AdminDto dto);

    public void deleteEntity(long id);
}
