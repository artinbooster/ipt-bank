package com.boosterltd.iptbank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.boosterltd.iptbank.dto.NewsDto;
import com.boosterltd.iptbank.mapper.NewsMapper;
import com.boosterltd.iptbank.model.entity.NewsEntity;
import com.boosterltd.iptbank.repository.NewsRepository;
import com.boosterltd.iptbank.service.NewsService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
        /* -- Fields -- */
    private final NewsMapper mapper;
    private final NewsRepository repository;
        /* -- Public Non-static Methods -- */
    @Override
    public NewsDto createEntity(@NonNull NewsDto dto) {
        final NewsEntity entity = new NewsEntity();
        mapper.updateEntityFromDto(dto, entity);
        final NewsEntity savedEntity = repository.save(entity);
        
        return mapper.toDto(savedEntity);
    }

    @Override
    public NewsDto getDto(String id) {
        final NewsEntity entity = NewsService.getEntity(repository, id);
        return mapper.toDto(entity);
    }
    @Override
    public List<NewsDto> getDtoAll() {
        final List<NewsEntity> entityList = repository.findAll();
        return (
            entityList.stream().map(
                (entity) -> { return mapper.toDto(entity); }
            ).collect(Collectors.toList())
        );
    }

    @Override
    public NewsDto updateEntity(String id, @NonNull NewsDto dto) {
        final NewsEntity entity = NewsService.getEntity(repository, id);
        mapper.updateEntityFromDto(dto, entity);
        final NewsEntity savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public void deleteEntity(String id) {
        final NewsEntity entity = NewsService.getEntity(repository, id);
        repository.delete(entity);
    }
}
