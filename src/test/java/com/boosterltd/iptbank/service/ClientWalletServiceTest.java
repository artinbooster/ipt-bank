package com.boosterltd.iptbank.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.boosterltd.iptbank.dto.ClientWalletDto;
import com.boosterltd.iptbank.model.entity.ClientWalletEntity;
import com.boosterltd.iptbank.repository.ClientWalletRepository;

@SpringBootTest
public class ClientWalletServiceTest {
    @Autowired private ClientWalletService service;
    @MockitoBean private ClientWalletRepository repository;
    private static final Long clientId = 1L;
    private static final byte irjAmount = 02;
    private static final short irrAmount = 19;

    @Test
    public void ClientWalletService_GetDto() {
        final ClientWalletEntity savedEntity = new ClientWalletEntity(
            clientId, irjAmount, irrAmount
        );
        final ClientWalletDto correctSavedDto = new ClientWalletDto(
            clientId, irjAmount, irrAmount
        );
        Mockito.when(repository.findById(clientId)).thenReturn(Optional.of(savedEntity));
        
        final ClientWalletDto savedDto = service.getDto(clientId);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }
    @Test
    public void ClientWalletService_GetDtoAll() {
        final Long client1Id = clientId;
        final Long client2Id = 2L;
        final ClientWalletEntity savedEntity1 = new ClientWalletEntity(
            client1Id, irjAmount, irrAmount
        );
        final ClientWalletEntity savedEntity2 = new ClientWalletEntity(
            client2Id, irjAmount, irrAmount
        );
        final List<ClientWalletEntity> savedEntityList = List.of(savedEntity1, savedEntity2);
        final ClientWalletDto correctSavedDto1 = new ClientWalletDto(
            client1Id, irjAmount, irrAmount
        );
        final ClientWalletDto correctSavedDto2 = new ClientWalletDto(
            client2Id, irjAmount, irrAmount
        );
        final List<ClientWalletDto> correctSavedDtoList = List.of(correctSavedDto1, correctSavedDto2);
        Mockito.when(repository.findAll()).thenReturn(savedEntityList);
        
        final List<ClientWalletDto> savedDtoList = service.getDtoAll();

        for (int i = 0; i < savedDtoList.size(); i += 1) {
            final ClientWalletDto savedDto = savedDtoList.get(i);
            final ClientWalletDto correctSavedDto = correctSavedDtoList.get(i);
            Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
        }
    }

    @Test
    public void ClientWalletService_UpdateEntity() {
        final byte oldIrjAmount = 0;
        final short oldIrrAmount = 0;
        final ClientWalletEntity oldSavedEntity = new ClientWalletEntity(
            clientId, oldIrjAmount, oldIrrAmount
        );
        final byte newIrjAmount = irjAmount;
        final short newIrrAmount = irrAmount;
        final ClientWalletDto dto = new ClientWalletDto(
            null, newIrjAmount, newIrrAmount
        );
        final ClientWalletEntity newSavedEntity = new ClientWalletEntity(
            clientId, newIrjAmount, newIrrAmount
        );
        final ClientWalletDto correctSavedDto = new ClientWalletDto(
            clientId, newIrjAmount, newIrrAmount
        );
        Mockito.when(repository.findById(clientId)).thenReturn(Optional.of(oldSavedEntity));
        Mockito.when(repository.save(oldSavedEntity)).thenReturn(newSavedEntity);
        
        final ClientWalletDto savedDto = service.updateEntity(clientId, dto);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }
}
