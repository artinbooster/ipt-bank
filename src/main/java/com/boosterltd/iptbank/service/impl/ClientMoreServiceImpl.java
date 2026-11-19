package com.boosterltd.iptbank.service.impl;

import static com.boosterltd.lib.exception.Other.impossible;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.ClientDto;
import com.boosterltd.iptbank.dto.ClientTradeDto;
import com.boosterltd.iptbank.dto.TradeOptionListDto;
import com.boosterltd.iptbank.mapper.ClientMapper;
import com.boosterltd.iptbank.model.ClientWalletSlot;
import com.boosterltd.iptbank.model.InventorySlot;
import com.boosterltd.iptbank.model.InventorySlot.BoundInventorySlot;
import com.boosterltd.iptbank.model.entity.ClientEntity;
import com.boosterltd.iptbank.model.entity.ClientInventoryEntity;
import com.boosterltd.iptbank.model.entity.ClientWalletEntity;
import com.boosterltd.iptbank.model.entity.TradeTargetItemEntity;
import com.boosterltd.iptbank.model.entity.TradeTargetItemPriceEntity;
import com.boosterltd.iptbank.model.trade.TradeDirection;
import com.boosterltd.iptbank.repository.ClientInventoryRepository;
import com.boosterltd.iptbank.repository.ClientRepository;
import com.boosterltd.iptbank.repository.ClientWalletRepository;
import com.boosterltd.iptbank.repository.TradeTargetItemPriceRepository;
import com.boosterltd.iptbank.repository.TradeTargetItemRepository;
import com.boosterltd.iptbank.service.ClientInventoryService;
import com.boosterltd.iptbank.service.ClientMoreService;
import com.boosterltd.iptbank.service.ClientService;
import com.boosterltd.iptbank.service.ClientWalletService;

import lombok.NonNull;

@Service
public class ClientMoreServiceImpl implements ClientMoreService {
        /* -- Fields -- */
    private final ClientMapper mapper;
    private final ClientRepository repository;
    private final ClientInventoryRepository inventoryRepository;
    private final ClientWalletRepository walletRepository;
    private final TradeTargetItemRepository targetItemRepository;
    private final TradeTargetItemPriceRepository targetItemPriceRepository;
    private final Map<String, InventorySlot<?>> targetItemMap;
    private final Map<String, List<TradeTargetItemPriceEntity>> targetItemPriceMap;
    private final Map<String, BoundInventorySlot<?>> boundTargetItemMap;
        /* -- Constructors -- */
    public ClientMoreServiceImpl(
        ClientMapper mapper,
        ClientRepository repository,
        ClientInventoryRepository inventoryRepository,
        ClientWalletRepository walletRepository,
        TradeTargetItemRepository targetItemRepository,
        TradeTargetItemPriceRepository targetItemPriceRepository
    ) {
        this.mapper = mapper;
        this.repository = repository;
        this.inventoryRepository = inventoryRepository;
        this.walletRepository = walletRepository;
        this.targetItemRepository = targetItemRepository;
        this.targetItemPriceRepository = targetItemPriceRepository;

        final List<TradeTargetItemEntity> targetItemList = this.targetItemRepository.findAll();
        @SuppressWarnings("unchecked")
        final Map.Entry<String, InventorySlot<?>>[] targetItemEntryArray = (
            new Map.Entry[targetItemList.size()]
        );
            // Assign the entries of <targetItemMap>.
        for (int i = 0; i < targetItemEntryArray.length; i += 1) {
            final TradeTargetItemEntity targetItem = targetItemList.get(i);
            final String targetItemName = targetItem.getName();
            final Map.Entry<String, InventorySlot<?>> targetItemEntry;
            
            if (targetItemName.equals("IRJ")) {
                targetItemEntry = Map.entry(targetItemName, InventorySlot.ofProvided(
                    ClientWalletEntity.class,
                    (ClientWalletEntity ownerObject, int newAmount) -> { ownerObject.setIrjAmount((byte) newAmount); },
                    (ClientWalletEntity ownerObject) -> { return (int) ownerObject.getIrjAmount(); }
                ));
            }
            else if (targetItemName.equals("IRR")) {
                targetItemEntry = Map.entry(targetItemName, InventorySlot.ofProvided(
                    ClientWalletEntity.class,
                    (ClientWalletEntity ownerObject, int newAmount) -> { ownerObject.setIrrAmount((short) newAmount); },
                    (ClientWalletEntity ownerObject) -> { return (int) ownerObject.getIrrAmount(); }
                ));
            }
            else {
                targetItemEntry = Map.entry(targetItemName, InventorySlot.of(
                    ClientInventoryEntity.class, targetItemName
                ));
            }
            
            targetItemEntryArray[i] = targetItemEntry;
        }
        targetItemMap = Map.ofEntries(targetItemEntryArray);
            // Assign the entries of <targetItemPriceMap>.
        targetItemPriceMap = new HashMap<>(targetItemList.size());
        final List<TradeTargetItemPriceEntity> targetItemPriceList = this.targetItemPriceRepository.findAll();
        for (final TradeTargetItemPriceEntity targetItemPrice : targetItemPriceList) {
            final long targetItemId = targetItemPrice.getTradeTargetItemId();
            final TradeTargetItemEntity targetItem = this.targetItemRepository.findById(targetItemId).orElseThrow(
                () -> { return new RuntimeException(
                    "A <TradeTargetItemPrice> refers to a <TradeTargetItem> which doesn't exist."
                ); }
            );
            final String targetItemName = targetItem.getName();
            List<TradeTargetItemPriceEntity> thisTargetItemPriceList = targetItemPriceMap.get(targetItemName);
                // Handle edge cases.
            if (thisTargetItemPriceList == null) {
                final List<TradeTargetItemPriceEntity> newList = new ArrayList<>();
                targetItemPriceMap.put(targetItemName, newList);
                thisTargetItemPriceList = newList;
            }

            thisTargetItemPriceList.add(targetItemPrice);
        }
        boundTargetItemMap = new HashMap<>(targetItemMap.size());
    }
        /* -- Private Methods -- */
    private void bindAllItems(
        @NonNull ClientWalletEntity walletEntity, @NonNull ClientInventoryEntity inventoryEntity
    ) {
        for (final ClientWalletSlot walletItem : ClientWalletSlot.values()) {
            walletItem.setBoundInventorySlot(
                walletItem.getInventorySlot().getBound(walletEntity)
            );
        }
        for (final Map.Entry<String, InventorySlot<?>> targetItemEntry : targetItemMap.entrySet()) {
            switch (targetItemEntry.getValue().getOwnerClass()) {
                case Class<?> classX when (classX == ClientWalletEntity.class) -> {
                    boundTargetItemMap.put(
                        targetItemEntry.getKey(),
                        targetItemEntry.getValue().getBoundInRuntime(walletEntity)
                    );
                }
                case Class<?> classX when (classX == ClientInventoryEntity.class) -> {
                    boundTargetItemMap.put(
                        targetItemEntry.getKey(),
                        targetItemEntry.getValue().getBoundInRuntime(inventoryEntity)
                    );
                }
                default -> { throw impossible(); }
            }
        }
    }
    
    private static void validateWalletState(
        int neededIrjAmount, int availableIrjAmount, int neededIrrAmount, int availableIrrAmount
    ) {
        if (!(neededIrjAmount <= availableIrjAmount)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Please enter the values of the source item's fields less than or equal to your wallet. You don't have enough money to afford this right now."
            );
        }
        
        if (!(neededIrrAmount <= availableIrrAmount)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Please enter the values of the source item's fields less than or equal to your wallet. You don't have enough money to afford this right now."
            );
        }
    }
    private static void validateInventoryState(
        int wantedTradeTargetItemAmount, int availableTradeTargetItemAmount
    ) {
        if (!(wantedTradeTargetItemAmount <= availableTradeTargetItemAmount)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Please enter the values of the target item's fields less than or equal to your inventory. You don't have enough amount of the item to undo the trade right now."
            );
        }
    }

    private static void validateTrade(
        int gottenTargetItemAmount, int givenIrjAmount, int givenIrrAmount,
        @NonNull List<TradeTargetItemPriceEntity> targetItemPriceList, @NonNull TradeDirection tradeDirection,
        int availableIrjAmount, int availableIrrAmount, int availableTargetItemAmount
    ) {
        if (!(gottenTargetItemAmount >= 0)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Please enter a non-negative number for amount of the target item."
            );
        }

        int neededIrjAmount = 0;
        int neededIrrAmount = 0;
        boolean doValuesMatch = false;

        for (final TradeTargetItemPriceEntity targetItemPrice : targetItemPriceList) {
            if (gottenTargetItemAmount % targetItemPrice.getAmountInPackage() != 0) {
                continue;
            }

            int packageAmount = gottenTargetItemAmount / targetItemPrice.getAmountInPackage();            
            neededIrjAmount = targetItemPrice.getPriceIrjAmount() * packageAmount;
            neededIrrAmount = targetItemPrice.getPriceIrrAmount() * packageAmount;

            if (givenIrjAmount == neededIrjAmount && givenIrrAmount == neededIrrAmount) {
                doValuesMatch = true;
                break;
            }
        }

        if (!doValuesMatch) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Please match the amount of all the items of the trade. You can take a look at the trade's guide if you need."
            );
        }

        switch (tradeDirection) {
            case DO -> {
                validateWalletState(
                    neededIrjAmount, availableIrjAmount, neededIrrAmount, availableIrrAmount
                );
            }
            case UNDO -> {
                validateInventoryState(gottenTargetItemAmount, availableTargetItemAmount);
            }
            default -> { throw impossible(); }
        }
    }
        /* -- Public Methods -- */
    @Override
    public TradeOptionListDto getTradeOptionDto() {
        final List<String> tradeOptionList = new ArrayList<>();
        for (final Map.Entry<String, InventorySlot<?>> targetItem : targetItemMap.entrySet()) {
            String targetItemName = targetItem.getKey();
            for (final TradeTargetItemPriceEntity targetItemPrice : targetItemPriceMap.get(targetItem.getKey())) {
                int amountInPackage = targetItemPrice.getAmountInPackage();
                int priceIrjAmount = targetItemPrice.getPriceIrjAmount();
                int priceIrrAmount = targetItemPrice.getPriceIrrAmount();
                if (priceIrjAmount != 0 && priceIrrAmount != 0) {
                    tradeOptionList.add("IRJ %1$d + IRR %2$d -> %3$d %4$s".formatted(
                        priceIrjAmount, priceIrrAmount, amountInPackage, targetItemName
                    ));
                }
                else if (priceIrjAmount != 0) {
                    tradeOptionList.add("IRJ %1$d -> %2$d %3$s".formatted(
                        priceIrjAmount, amountInPackage, targetItemName
                    ));
                }
                else if (priceIrrAmount != 0) {
                    tradeOptionList.add("IRR %1$d -> %2$d %3$s".formatted(
                        priceIrrAmount, amountInPackage, targetItemName
                    ));
                }
                else { throw impossible(); }
            }
        }
        return new TradeOptionListDto(tradeOptionList);
    }
    
    @Override
    public ClientDto trade(long id, @NonNull ClientTradeDto tradeDto) {
        final ClientEntity entity = ClientService.getEntity(repository, id);
        final ClientInventoryEntity inventoryEntity = ClientInventoryService.getEntity(inventoryRepository, id);
        final ClientWalletEntity walletEntity = ClientWalletService.getEntity(walletRepository, id);
        bindAllItems(walletEntity, inventoryEntity);
        final int gottenTargetItemAmount = tradeDto.getTargetItem().getAmount();
        final int givenIrjAmount = tradeDto.getSourceItem().getIrjAmount();
        final int givenIrrAmount = tradeDto.getSourceItem().getIrrAmount();
        final String targetItemName = tradeDto.getTargetItem().getName();
        final List<TradeTargetItemPriceEntity> targetItemPriceList = targetItemPriceMap.get(targetItemName);
        final TradeDirection tradeDirection = TradeDirection.DO;
        final BoundInventorySlot<?> targetItem = boundTargetItemMap.get(targetItemName);
        final int availableTargetItemAmount = targetItem.getAmount();

        validateTrade(
            gottenTargetItemAmount, givenIrjAmount, givenIrrAmount,
            targetItemPriceList, tradeDirection,
            givenIrjAmount, givenIrrAmount, availableTargetItemAmount
        );

        ClientWalletSlot.IRJ.getBoundInventorySlot().changeAmount(- givenIrjAmount);
        ClientWalletSlot.IRR.getBoundInventorySlot().changeAmount(- givenIrrAmount);
        targetItem.changeAmount(+ gottenTargetItemAmount);

        ClientEntity savedEntity = repository.save(entity);
        inventoryRepository.save(inventoryEntity);
        walletRepository.save(walletEntity);
        return mapper.toDto(savedEntity);
    }
    @Override
    public ClientDto tradeUndo(long id, @NonNull ClientTradeDto tradeDto) {
        final ClientEntity entity = ClientService.getEntity(repository, id);
        final ClientInventoryEntity inventoryEntity = ClientInventoryService.getEntity(inventoryRepository, id);
        final ClientWalletEntity walletEntity = ClientWalletService.getEntity(walletRepository, id);
        bindAllItems(walletEntity, inventoryEntity);
        final int gottenTargetItemAmount = tradeDto.getTargetItem().getAmount();
        final int givenIrjAmount = tradeDto.getSourceItem().getIrjAmount();
        final int givenIrrAmount = tradeDto.getSourceItem().getIrrAmount();
        final String targetItemName = tradeDto.getTargetItem().getName();
        final List<TradeTargetItemPriceEntity> targetItemPriceList = targetItemPriceMap.get(targetItemName);
        final TradeDirection tradeDirection = TradeDirection.UNDO;
        final BoundInventorySlot<?> targetItem = boundTargetItemMap.get(targetItemName);
        final int availableTargetItemAmount = targetItem.getAmount();

        validateTrade(
            gottenTargetItemAmount, givenIrjAmount, givenIrrAmount,
            targetItemPriceList, tradeDirection,
            givenIrjAmount, givenIrrAmount, availableTargetItemAmount
        );

        targetItem.changeAmount(- tradeDto.getTargetItem().getAmount());
        int spentIrjAmount = tradeDto.getSourceItem().getIrjAmount();
        int spentIrrAmount = tradeDto.getSourceItem().getIrrAmount();
        int fineIrjAmount = (spentIrjAmount == 0) ? 0 : 1;
        int fineIrrAmount = (spentIrrAmount == 0) ? 0 : 5;
        ClientWalletSlot.IRJ.getBoundInventorySlot().changeAmount(+ spentIrjAmount - fineIrjAmount);
        ClientWalletSlot.IRR.getBoundInventorySlot().changeAmount(+ spentIrrAmount - fineIrrAmount);
        ClientEntity savedEntity = repository.save(entity);
        inventoryRepository.save(inventoryEntity);
        walletRepository.save(walletEntity);
        return mapper.toDto(savedEntity);
    }
}
