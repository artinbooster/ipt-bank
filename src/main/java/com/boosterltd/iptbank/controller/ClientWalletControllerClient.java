package com.boosterltd.iptbank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.ClientWalletDto;
import com.boosterltd.iptbank.service.ClientWalletService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-client/client-wallet")
@RestController @RequiredArgsConstructor
public class ClientWalletControllerClient {
    private final ClientWalletService service;
    
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<ClientWalletDto> getDto(
        @PathVariable(value = "id") long id
    ) {
        ClientWalletDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
