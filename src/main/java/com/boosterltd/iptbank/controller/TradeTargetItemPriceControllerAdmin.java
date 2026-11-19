package com.boosterltd.iptbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.TradeTargetItemPriceDto;
import com.boosterltd.iptbank.helper.ResponseEntityMessage;
import com.boosterltd.iptbank.service.TradeTargetItemPriceService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/private-admin/trade-target-item-price")
@RestController @RequiredArgsConstructor
public class TradeTargetItemPriceControllerAdmin {
    private final TradeTargetItemPriceService service;

    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity<TradeTargetItemPriceDto> createEntity(
        @RequestBody TradeTargetItemPriceDto dto
    ) {
        TradeTargetItemPriceDto savedDto = service.createEntity(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<TradeTargetItemPriceDto> getDto(
        @PathVariable(name = "id") long id
    ) {
        TradeTargetItemPriceDto dto = service.getDto(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<TradeTargetItemPriceDto>> getDtoAll() {
        List<TradeTargetItemPriceDto> dtoList = service.getDtoAll();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<TradeTargetItemPriceDto> updateEntity(
        @PathVariable(name = "id") long id,
        @RequestBody TradeTargetItemPriceDto dto
    ) {
        TradeTargetItemPriceDto savedDto = service.updateEntity(id, dto);
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
    
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/change-numbers")
    public ResponseEntity<TradeTargetItemPriceDto> changeNumbers(
        @PathVariable(name = "id") long id,
        @RequestBody TradeTargetItemPriceDto dto
    ) {
        TradeTargetItemPriceDto savedDto = service.changeNumbers(id, dto);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
}
