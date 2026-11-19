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

import com.boosterltd.iptbank.dto.AdminDto;
import com.boosterltd.iptbank.model.entity.AdminEntity;
import com.boosterltd.iptbank.repository.AdminRepository;

@SpringBootTest
public class AdminServiceTest {
    @Autowired private AdminService service;
    @MockitoBean private AdminRepository repository;
    private static final Long entityId = 1L;
    private static final String username = "bruce_wayne";
    private static final String plainPassword = "i_am_batman";
    private static final String hashedPassword = getHashedVersion(plainPassword);

    @Test
    public void AdminService_CreateEntity() {
        final AdminDto dto = new AdminDto(
            null, null, plainPassword, username
        );
        final AdminEntity savedEntity = new AdminEntity(
            entityId, hashedPassword, username
        );
        final AdminDto correctSavedDto = new AdminDto(
            entityId, hashedPassword, null, username
        );
        Mockito.when(repository.save(Mockito.any(AdminEntity.class))).thenReturn(savedEntity);
        
        final AdminDto savedDto = service.createEntity(dto);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }

    @Test
    public void AdminService_GetDto() {
        final AdminEntity savedEntity = new AdminEntity(
            entityId, hashedPassword, username
        );
        final AdminDto correctSavedDto = new AdminDto(
            entityId, hashedPassword, null, username
        );
        Mockito.when(repository.findById(entityId)).thenReturn(Optional.of(savedEntity));
        
        final AdminDto savedDto = service.getDto(entityId);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }
    @Test
    public void AdminService_GetDtoAll() {
        final Long entity1Id = entityId;
        final Long entity2Id = 2L;
        final String username1 = username;
        final String username2 = "bruce_wayne_again";
        final AdminEntity savedEntity1 = new AdminEntity(
            entity1Id, hashedPassword, username1
        );
        final AdminEntity savedEntity2 = new AdminEntity(
            entity2Id, hashedPassword, username2
        );
        final List<AdminEntity> savedEntityList = List.of(savedEntity1, savedEntity2);
        final AdminDto correctSavedDto1 = new AdminDto(
            entity1Id, hashedPassword, null, username1
        );
        final AdminDto correctSavedDto2 = new AdminDto(
            entity2Id, hashedPassword, null, username2
        );
        final List<AdminDto> correctSavedDtoList = List.of(correctSavedDto1, correctSavedDto2);
        Mockito.when(repository.findAll()).thenReturn(savedEntityList);
        
        final List<AdminDto> savedDtoList = service.getDtoAll();

        for (int i = 0; i < savedDtoList.size(); i += 1) {
            final AdminDto savedDto = savedDtoList.get(i);
            final AdminDto correctSavedDto = correctSavedDtoList.get(i);
            Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
        }
    }

    @Test
    public void AdminService_UpdateEntity() {
        final String oldUsername = null;
        final String oldHashedPassword = null;
        final AdminEntity oldSavedEntity = new AdminEntity(
            entityId, oldHashedPassword, oldUsername
        );
        final String newUsername = username;
        final String newPlainPassword = plainPassword;
        final String newHashedPassword = hashedPassword;
        final AdminDto dto = new AdminDto(
            null, null, newPlainPassword, newUsername
        );
        final AdminEntity newSavedEntity = new AdminEntity(
            entityId, newHashedPassword, newUsername
        );
        final AdminDto correctSavedDto = new AdminDto(
            entityId, newHashedPassword, null, newUsername
        );
        Mockito.when(repository.findById(entityId)).thenReturn(Optional.of(oldSavedEntity));
        Mockito.when(repository.save(oldSavedEntity)).thenReturn(newSavedEntity);
        
        final AdminDto savedDto = service.updateEntity(entityId, dto);

        Assertions.assertThat(savedDto).isNotNull();
        Assertions.assertThat(savedDto).isEqualTo(correctSavedDto);
    }

    @Test
    public void AdminService_DeleteEntity() {
        final AdminEntity savedEntity = new AdminEntity(
            entityId, hashedPassword, username
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
