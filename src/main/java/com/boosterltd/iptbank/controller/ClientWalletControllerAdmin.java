package com.boosterltd.iptbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.ClientWalletDto;
import com.boosterltd.iptbank.service.ClientWalletService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-admin/client-wallet")
@RestController @RequiredArgsConstructor
public class ClientWalletControllerAdmin {
    private final ClientWalletService service;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<ClientWalletDto> getDto(
        @PathVariable("id") long id
    ) {
        ClientWalletDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<ClientWalletDto>> getDtoAll() {
        List<ClientWalletDto> dtoList = service.getDtoAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<ClientWalletDto> updateEntity(
        @PathVariable("id") long id,
        @RequestBody ClientWalletDto dto
    ) {
        ClientWalletDto savedDto = service.updateEntity(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/change-numbers")
    public ResponseEntity<ClientWalletDto> changeNumbers(
        @PathVariable("id") long id,
        @RequestBody ClientWalletDto dto
    ) {
        ClientWalletDto savedDto = service.changeNumbers(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
}
