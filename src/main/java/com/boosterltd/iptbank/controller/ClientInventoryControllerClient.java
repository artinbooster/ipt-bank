package com.boosterltd.iptbank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.ClientInventoryDto;
import com.boosterltd.iptbank.service.ClientInventoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-client/client-inventory")
@RestController @RequiredArgsConstructor
public class ClientInventoryControllerClient {
    private final ClientInventoryService service;
    
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<ClientInventoryDto> getDto(
        @PathVariable(value = "id") long id
    ) {
        ClientInventoryDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
