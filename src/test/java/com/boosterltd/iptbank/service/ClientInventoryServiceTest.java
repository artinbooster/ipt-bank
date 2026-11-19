package com.boosterltd.iptbank.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.boosterltd.iptbank.dto.ClientInventoryDto;
import com.boosterltd.iptbank.model.entity.ClientInventoryEntity;
import com.boosterltd.iptbank.repository.ClientInventoryRepository;

@SpringBootTest
public class ClientInventoryServiceTest {
    @Autowired private ClientInventoryService service;
    @MockitoBean private ClientInventoryRepository repository;
    private static final Long clientId = 1L;
    private static final byte hotChocolateAmount = 1;
    private static final byte shekarestanSeriesEpisodeAmount = 2;
    private static final byte stillwaterSeriesEpisodeAmount = 3;
    private static final byte vpnSubscriptionAmount = 4;

    @Test
    public void ClientInventoryService_GetDto() {
        final ClientInventoryEntity savedEntity = new ClientInventoryEntity(
            clientId, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        final ClientInventoryDto correctSavedDto = new ClientInventoryDto(
            clientId, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        Mockito.when(repository.findById(clientId)).thenReturn(Optional.of(savedEntity));
        
        final ClientInventoryDto savedDto = service.getDto(clientId);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }
    @Test
    public void ClientInventoryService_GetDtoAll() {
        final Long client1Id = clientId;
        final Long client2Id = 2L;
        final ClientInventoryEntity savedEntity1 = new ClientInventoryEntity(
            client1Id, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        final ClientInventoryEntity savedEntity2 = new ClientInventoryEntity(
            client2Id, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        final List<ClientInventoryEntity> savedEntityList = List.of(savedEntity1, savedEntity2);
        final ClientInventoryDto correctSavedDto1 = new ClientInventoryDto(
            client1Id, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        final ClientInventoryDto correctSavedDto2 = new ClientInventoryDto(
            client2Id, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        final List<ClientInventoryDto> correctSavedDtoList = List.of(correctSavedDto1, correctSavedDto2);
        Mockito.when(repository.findAll()).thenReturn(savedEntityList);
        
        final List<ClientInventoryDto> savedDtoList = service.getDtoAll();

        for (int i = 0; i < savedDtoList.size(); i += 1) {
            final ClientInventoryDto savedDto = savedDtoList.get(i);
            final ClientInventoryDto correctSavedDto = correctSavedDtoList.get(i);
            Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
        }
    }

    @Test
    public void ClientInventoryService_UpdateEntity() {
        final byte oldHotChocolateAmount = 0;
        final byte oldShekarestanSeriesEpisodeAmount = 0;
        final byte oldStillwaterSeriesEpisodeAmount = 0;
        final byte oldVpnSubscriptionAmount = 0;
        final ClientInventoryEntity oldSavedEntity = new ClientInventoryEntity(
            clientId, oldHotChocolateAmount, oldShekarestanSeriesEpisodeAmount,
            oldStillwaterSeriesEpisodeAmount, oldVpnSubscriptionAmount
        );
        final byte newHotChocolateAmount = hotChocolateAmount;
        final byte newShekarestanSeriesEpisodeAmount = shekarestanSeriesEpisodeAmount;
        final byte newStillwaterSeriesEpisodeAmount = stillwaterSeriesEpisodeAmount;
        final byte newVpnSubscriptionAmount = vpnSubscriptionAmount;
        final ClientInventoryDto dto = new ClientInventoryDto(
            null, newHotChocolateAmount, newShekarestanSeriesEpisodeAmount,
            newStillwaterSeriesEpisodeAmount, newVpnSubscriptionAmount
        );
        final ClientInventoryEntity newSavedEntity = new ClientInventoryEntity(
            clientId, newHotChocolateAmount, newShekarestanSeriesEpisodeAmount,
            newStillwaterSeriesEpisodeAmount, newVpnSubscriptionAmount
        );
        final ClientInventoryDto correctSavedDto = new ClientInventoryDto(
            clientId, newHotChocolateAmount, newShekarestanSeriesEpisodeAmount,
            newStillwaterSeriesEpisodeAmount, newVpnSubscriptionAmount
        );
        Mockito.when(repository.findById(clientId)).thenReturn(Optional.of(oldSavedEntity));
        Mockito.when(repository.save(oldSavedEntity)).thenReturn(newSavedEntity);
        
        final ClientInventoryDto savedDto = service.updateEntity(clientId, dto);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }
}
