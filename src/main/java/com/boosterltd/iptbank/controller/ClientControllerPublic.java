package com.boosterltd.iptbank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boosterltd.iptbank.dto.TradeOptionListDto;
import com.boosterltd.iptbank.service.ClientMoreService;

import lombok.RequiredArgsConstructor;

@RequestMapping(path = "/api/public/client")
@RestController @RequiredArgsConstructor
public class ClientControllerPublic {
    private final ClientMoreService moreService;
    
    @RequestMapping(method = RequestMethod.GET, path = "/trade-option")
    public ResponseEntity<TradeOptionListDto> getTradeOptionDto() {
        TradeOptionListDto tradeOptionDto = moreService.getTradeOptionDto();
        return new ResponseEntity<>(tradeOptionDto, HttpStatus.OK);
    }
}
