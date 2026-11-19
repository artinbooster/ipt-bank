package com.boosterltd.iptbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.ClientDto;
import com.boosterltd.iptbank.helper.ResponseEntityMessage;
import com.boosterltd.iptbank.service.ClientService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-admin/client")
@RestController @RequiredArgsConstructor
public class ClientControllerAdmin {
    private final ClientService service;

    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity<ClientDto> createEntity(
        @RequestBody ClientDto dto
    ) {
        ClientDto savedDto = service.createEntity(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<ClientDto> getDto(
        @PathVariable("id") long id
    ) {
        ClientDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<ClientDto>> getDtoAll() {
        List<ClientDto> dtoList = service.getDtoAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<ClientDto> updateEntity(
        @PathVariable("id") long id,
        @RequestBody ClientDto dto
    ) {
        ClientDto savedDto = service.updateEntity(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<String> deleteEntity(
        @PathVariable("id") long id
    ) {
        service.deleteEntity(id);
        return new ResponseEntity<>(
            ResponseEntityMessage.SUCCESSFUL_DELETE.getMessage().formatted("client", "ID", String.valueOf(id)),
            HttpStatus.OK
        );
    }
}
