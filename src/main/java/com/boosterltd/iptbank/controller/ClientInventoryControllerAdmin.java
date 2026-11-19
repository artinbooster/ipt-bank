package com.boosterltd.iptbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.ClientInventoryDto;
import com.boosterltd.iptbank.service.ClientInventoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-admin/client-inventory")
@RestController @RequiredArgsConstructor
public class ClientInventoryControllerAdmin {
    private final ClientInventoryService service;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<ClientInventoryDto> getDto(
        @PathVariable("id") long id
    ) {
        ClientInventoryDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<ClientInventoryDto>> getDtoAll() {
        List<ClientInventoryDto> dtoList = service.getDtoAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<ClientInventoryDto> updateEntity(
        @PathVariable("id") long id,
        @RequestBody ClientInventoryDto dto
    ) {
        ClientInventoryDto savedDto = service.updateEntity(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/change-numbers")
    public ResponseEntity<ClientInventoryDto> changeNumbers(
        @PathVariable("id") long id,
        @RequestBody ClientInventoryDto dto
    ) {
        ClientInventoryDto savedDto = service.changeNumbers(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
}
