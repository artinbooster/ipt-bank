package com.boosterltd.iptbank.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.NewsDto;
import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.NewsEntity;
import com.boosterltd.iptbank.repository.NewsRepository;

import lombok.NonNull;

public interface NewsService {
    public static NewsEntity getEntity(
        @NonNull NewsRepository repository, String id
    ) {
        final NewsEntity entity = repository.findById(id).orElseThrow(
            () -> { return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted("news", "ID", String.valueOf(id))
            ); }
        );
        return entity;
    }
    
    public NewsDto createEntity(@NonNull NewsDto dto);

    public NewsDto getDto(String id);
    public List<NewsDto> getDtoAll();

    public NewsDto updateEntity(String id, @NonNull NewsDto dto);

    public void deleteEntity(String id);
}
