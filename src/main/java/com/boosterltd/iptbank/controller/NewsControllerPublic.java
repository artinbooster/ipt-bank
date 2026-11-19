package com.boosterltd.iptbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.NewsDto;
import com.boosterltd.iptbank.service.NewsService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/public/news")
@RestController @RequiredArgsConstructor
public class NewsControllerPublic {
    private final NewsService service;

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
}
