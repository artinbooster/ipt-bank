package com.boosterltd.iptbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.AdminDto;
import com.boosterltd.iptbank.helper.ResponseEntityMessage;
import com.boosterltd.iptbank.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-admin/admin")
@RestController @RequiredArgsConstructor
public class AdminControllerAdmin {
    private final AdminService service;

    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity<AdminDto> createEntity(
        @RequestBody AdminDto dto
    ) {
        AdminDto savedDto = service.createEntity(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<AdminDto> getDto(
        @PathVariable("id") long id
    ) {
        AdminDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<AdminDto>> getDtoAll() {
        List<AdminDto> dtoList = service.getDtoAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<AdminDto> updateEntity(
        @PathVariable("id") long id,
        @RequestBody AdminDto dto
    ) {
        AdminDto savedDto = service.updateEntity(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<String> deleteEntity(
        @PathVariable("id") long id
    ) {
        service.deleteEntity(id);
        return new ResponseEntity<>(
            ResponseEntityMessage.SUCCESSFUL_DELETE.getMessage().formatted("admin", "ID", String.valueOf(id)),
            HttpStatus.OK
        );
    }
}
