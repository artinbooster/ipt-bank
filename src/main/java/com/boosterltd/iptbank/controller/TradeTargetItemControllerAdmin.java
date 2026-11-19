package com.boosterltd.iptbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.TradeTargetItemDto;
import com.boosterltd.iptbank.helper.ResponseEntityMessage;
import com.boosterltd.iptbank.service.TradeTargetItemService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-admin/trade-target-item")
@RestController @RequiredArgsConstructor
public class TradeTargetItemControllerAdmin {
    private final TradeTargetItemService service;

    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity<TradeTargetItemDto> createEntity(
        @RequestBody TradeTargetItemDto dto
    ) {
        TradeTargetItemDto savedDto = service.createEntity(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<TradeTargetItemDto> getDto(
        @PathVariable(name = "id") long id
    ) {
        TradeTargetItemDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<TradeTargetItemDto>> getDtoAll() {
        List<TradeTargetItemDto> dtoList = service.getDtoAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<TradeTargetItemDto> updateEntity(
        @PathVariable(name = "id") long id,
        @RequestBody TradeTargetItemDto dto
    ) {
        TradeTargetItemDto savedDto = service.updateEntity(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<String> deleteEntity(
        @PathVariable(name = "id") long id
    ) {
        service.deleteEntity(id);
        return new ResponseEntity<>(
            ResponseEntityMessage.SUCCESSFUL_DELETE.getMessage().formatted("trade target item", "ID", String.valueOf(id)),
            HttpStatus.OK
        );
    }
}
