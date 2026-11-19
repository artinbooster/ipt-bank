package com.boosterltd.iptbank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.ClientDto;
import com.boosterltd.iptbank.dto.ClientTradeDto;
import com.boosterltd.iptbank.service.ClientMoreService;
import com.boosterltd.iptbank.service.ClientService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-client/client")
@RestController @RequiredArgsConstructor
public class ClientControllerClient {
    private final ClientService service;
    private final ClientMoreService moreService;
    
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<ClientDto> getDto(
        @PathVariable(value = "id") long id
    ) {
        ClientDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.PUT, path = "/{id}/trade")
    public ResponseEntity<ClientDto> clientTrade(
        @PathVariable(value = "id") long id,
        @RequestBody ClientTradeDto tradeDto
    ) {
        ClientDto savedDto = moreService.trade(id, tradeDto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.PUT, path = "/{id}/trade-undo")
    public ResponseEntity<ClientDto> clientTradeUndo(
        @PathVariable(value = "id") long id,
        @RequestBody ClientTradeDto tradeDto
    ) {
        ClientDto savedDto = moreService.tradeUndo(id, tradeDto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
}
