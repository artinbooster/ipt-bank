package com.boosterltd.iptbank.service;

import com.boosterltd.iptbank.dto.ClientDto;
import com.boosterltd.iptbank.dto.ClientTradeDto;
import com.boosterltd.iptbank.dto.TradeOptionListDto;

import lombok.NonNull;

public interface ClientMoreService {
    public TradeOptionListDto getTradeOptionDto();

    public ClientDto trade(long id, @NonNull ClientTradeDto tradeDto);
    public ClientDto tradeUndo(long id, @NonNull ClientTradeDto tradeDto);
}
