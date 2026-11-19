package com.boosterltd.iptbank.service;

import static com.boosterltd.lib.security.Security.getHashedVersion;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;

import com.boosterltd.iptbank.dto.ClientDto;
import com.boosterltd.iptbank.model.entity.ClientEntity;
import com.boosterltd.iptbank.repository.ClientRepository;

@SpringBootTest
public class ClientServiceTest {
    @Autowired private ClientService service;
    @MockitoBean private ClientRepository repository;
    private static final Long entityId = 1L;
    private static final String emailAddress = "bruce_wayne@batman.net";
    private static final String gender = "MALE";
    private static final String givenName = "Bruce";
    private static final String surname = "Wayne";
    private static final String surnameSuffix = null;
    private static final String fullName = "Bruce Wayne";
    private static final String plainPassword = "38291232";
    private static final String hashedPassword = getHashedVersion(plainPassword);

    @Test
    public void ClientService_CreateEntity() {
        final ClientDto dto = new ClientDto(
            null, emailAddress, gender,
            new ClientDto.Name(givenName, surname, surnameSuffix, fullName),
            null, plainPassword
        );
        final ClientEntity savedEntity = new ClientEntity(
            entityId, emailAddress, gender, givenName,
            surname, surnameSuffix, fullName, hashedPassword
        );
        final ClientDto correctSavedDto = new ClientDto(
            entityId, emailAddress, gender,
            new ClientDto.Name(givenName, surname, surnameSuffix, fullName),
            hashedPassword, null
        );
        Mockito.when(repository.save(Mockito.any(ClientEntity.class))).thenReturn(savedEntity);
        
        final ClientDto savedDto = service.createEntity(dto);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }

    @Test
    public void ClientService_GetDto() {
        final ClientEntity savedEntity = new ClientEntity(
            entityId, emailAddress, gender, givenName,
            surname, surnameSuffix, fullName, hashedPassword
        );
        final ClientDto correctSavedDto = new ClientDto(
            entityId, emailAddress, gender,
            new ClientDto.Name(givenName, surname, surnameSuffix, fullName),
            hashedPassword, null
        );
        Mockito.when(repository.findById(entityId)).thenReturn(Optional.of(savedEntity));
        
        final ClientDto savedDto = service.getDto(entityId);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }
    @Test
    public void ClientService_GetDtoAll() {
        final Long entity1Id = entityId;
        final Long entity2Id = 2L;
        final String emailAddress1 = emailAddress;
        final String emailAddress2 = "bruce_wayne_again@batman.net";
        final String surname1 = surname;
        final String surname2 = "Wayne Again";
        final String fullName1 = fullName;
        final String fullName2 = "Bruce Wayne Again";
        final ClientEntity savedEntity1 = new ClientEntity(
            entity1Id, emailAddress1, gender, givenName,
            surname1, surnameSuffix, fullName1, hashedPassword
        );
        final ClientEntity savedEntity2 = new ClientEntity(
            entity2Id, emailAddress2, gender, givenName,
            surname2, surnameSuffix, fullName2, hashedPassword
        );
        final List<ClientEntity> savedEntityList = List.of(savedEntity1, savedEntity2);
        final ClientDto correctSavedDto1 = new ClientDto(
            entity1Id, emailAddress1, gender,
            new ClientDto.Name(givenName, surname1, surnameSuffix, fullName1),
            hashedPassword, null
        );
        final ClientDto correctSavedDto2 = new ClientDto(
            entity2Id, emailAddress2, gender,
            new ClientDto.Name(givenName, surname2, surnameSuffix, fullName2),
            hashedPassword, null
        );
        final List<ClientDto> correctSavedDtoList = List.of(correctSavedDto1, correctSavedDto2);
        Mockito.when(repository.findAll()).thenReturn(savedEntityList);
        
        final List<ClientDto> savedDtoList = service.getDtoAll();

        for (int i = 0; i < savedDtoList.size(); i += 1) {
            final ClientDto savedDto = savedDtoList.get(i);
            final ClientDto correctSavedDto = correctSavedDtoList.get(i);
            Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
        }
    }

    @Test
    public void ClientService_UpdateEntity() {
        final String oldEmailAddress = null;
        final String oldGender = null;
        final String oldGivenName = null;
        final String oldSurname = null;
        final String oldSurnameSuffix = null;
        final String oldFullName = null;
        final String oldHashedPassword = null;
        final ClientEntity oldSavedEntity = new ClientEntity(
            entityId, oldEmailAddress, oldGender, oldGivenName,
            oldSurname, oldSurnameSuffix, oldFullName, oldHashedPassword
        );
        final String newEmailAddress = emailAddress;
        final String newGender = gender;
        final String newGivenName = givenName;
        final String newSurname = surname;
        final String newSurnameSuffix = surnameSuffix;
        final String newFullName = fullName;
        final String newPlainPassword = plainPassword;
        final String newHashedPassword = hashedPassword;
        final ClientDto dto = new ClientDto(
            null, newEmailAddress, newGender,
            new ClientDto.Name(newGivenName, newSurname, newSurnameSuffix, newFullName),
            null, newPlainPassword
        );
        final ClientEntity newSavedEntity = new ClientEntity(
            entityId, newEmailAddress, newGender, newGivenName,
            newSurname, newSurnameSuffix, newFullName, newHashedPassword
        );
        final ClientDto correctSavedDto = new ClientDto(
            entityId, newEmailAddress, newGender,
            new ClientDto.Name(newGivenName, newSurname, newSurnameSuffix, newFullName),
            newHashedPassword, null
        );
        Mockito.when(repository.findById(entityId)).thenReturn(Optional.of(oldSavedEntity));
        Mockito.when(repository.save(oldSavedEntity)).thenReturn(newSavedEntity);
        
        final ClientDto savedDto = service.updateEntity(entityId, dto);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }

    @Test
    public void ClientService_DeleteEntity() {
        final ClientEntity savedEntity = new ClientEntity(
            entityId, emailAddress, gender, givenName,
            surname, surnameSuffix, fullName, hashedPassword
        );
        Mockito.when(repository.findById(entityId))
            .thenReturn(Optional.of(savedEntity))
            .thenReturn(Optional.empty());

        service.deleteEntity(entityId);

        Assertions.assertThatThrownBy(() -> {
            service.getDto(entityId);
        }).isInstanceOf(ResponseStatusException.class);
    }
}
