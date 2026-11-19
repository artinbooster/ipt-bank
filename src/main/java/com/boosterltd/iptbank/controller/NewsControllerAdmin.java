package com.boosterltd.iptbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.NewsDto;
import com.boosterltd.iptbank.helper.ResponseEntityMessage;
import com.boosterltd.iptbank.service.NewsService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-admin/news")
@RestController @RequiredArgsConstructor
public class NewsControllerAdmin {
    private final NewsService service;

    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity<NewsDto> createEntity(
        @RequestBody NewsDto dto
    ) {
        NewsDto savedDto = service.createEntity(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<NewsDto> getDto(
        @PathVariable("id") String id
    ) {
        NewsDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<NewsDto>> getDtoAll() {
        List<NewsDto> dtoList = service.getDtoAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<NewsDto> updateEntity(
        @PathVariable("id") String id,
        @RequestBody NewsDto dto
    ) {
        NewsDto savedDto = service.updateEntity(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<String> deleteEntity(
        @PathVariable("id") String id
    ) {
        service.deleteEntity(id);
        return new ResponseEntity<>(
            ResponseEntityMessage.SUCCESSFUL_DELETE.getMessage().formatted("news", "ID", String.valueOf(id)),
            HttpStatus.OK
        );
    }
}
