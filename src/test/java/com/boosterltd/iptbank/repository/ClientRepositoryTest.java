package com.boosterltd.iptbank.repository;

import static com.boosterltd.lib.security.Security.getHashedVersion;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.boosterltd.iptbank.model.entity.ClientEntity;

@DataJpaTest @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClientRepositoryTest {
    @Autowired private ClientRepository repository;
    private static final String emailAddress = "bruce_wayne@batman.net";
    private static final String gender = "MALE";
    private static final String givenName = "Bruce";
    private static final String surname = "Wayne";
    private static final String surnameSuffix = null;
    private static final String fullName = "Bruce Wayne";
    private static final String plainPassword = "38291232";
    private static final String hashedPassword = getHashedVersion(plainPassword);

    @Test
    public void ClientRepository_Save() {
        Long entityId = null;
        final ClientEntity entity = new ClientEntity(
            entityId, emailAddress, gender, givenName,
            surname, surnameSuffix, fullName, hashedPassword
        );

        final ClientEntity savedEntity = repository.save(entity);
        entityId = savedEntity.getId();
        entity.setId(entityId);

        Assertions.assertThat(savedEntity).isEqualTo(entity);
        Assertions.assertThat(entityId).isGreaterThan(0);
    }

    @Test
    public void ClientRepository_FindById() {
        Long entityId = null;
        final ClientEntity entity = new ClientEntity(
            entityId, emailAddress, gender, givenName,
            surname, surnameSuffix, fullName, hashedPassword
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        final ClientEntity foundEntity = repository.findById(entityId).get();

        Assertions.assertThat(foundEntity).isEqualTo(entity);
    }
    @Test
    public void ClientRepository_FindAll() {
        Long entity1Id = null;
        Long entity2Id = null;
        final String emailAddress1 = emailAddress;
        final String emailAddress2 = "bruce_wayne_again@batman.net";
        final String surname1 = surname;
        final String surname2 = "Wayne Again";
        final String fullName1 = fullName;
        final String fullName2 = "Bruce Wayne Again";
        final ClientEntity entity1 = new ClientEntity(
            entity1Id, emailAddress1, gender, givenName,
            surname1, surnameSuffix, fullName1, hashedPassword
        );
        final ClientEntity entity2 = new ClientEntity(
            entity1Id, emailAddress2, gender, givenName,
            surname2, surnameSuffix, fullName2, hashedPassword
        );
        final List<ClientEntity> correctFoundEntityList = List.of(entity1, entity2);
        entity1Id = repository.save(entity1).getId();
        entity2Id = repository.save(entity2).getId();
        entity1.setId(entity1Id);
        entity2.setId(entity2Id);

        final List<ClientEntity> foundEntityList = repository.findAll();

        for (int i = 0; i < foundEntityList.size(); i += 1) {
            final ClientEntity foundEntity = foundEntityList.get(i);
            final ClientEntity correctFoundEntity = correctFoundEntityList.get(i);
            Assertions.assertThat(foundEntity).isEqualTo(correctFoundEntity);
        }
    }

    @Test
    public void ClientRepository_Delete() {
        Long entityId = null;
        final String emailAddress = "bruce_wayne@batman.net";
        final String gender = "MALE";
        final String givenName = "Bruce";
        final String surname = "Wayne";
        final String surnameSuffix = null;
        final String fullName = "Bruce Wayne";
        final String hashedPassword = getHashedVersion("38291232");
        final ClientEntity entity = new ClientEntity(
            entityId, emailAddress, gender, givenName,
            surname, surnameSuffix, fullName, hashedPassword
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        repository.delete(entity);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
    @Test
    public void ClientRepository_DeleteById() {
        Long entityId = null;
        final String emailAddress = "bruce_wayne@batman.net";
        final String gender = "MALE";
        final String givenName = "Bruce";
        final String surname = "Wayne";
        final String surnameSuffix = null;
        final String fullName = "Bruce Wayne";
        final String hashedPassword = getHashedVersion("38291232");
        final ClientEntity entity = new ClientEntity(
            entityId, emailAddress, gender, givenName,
            surname, surnameSuffix, fullName, hashedPassword
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        repository.deleteById(entityId);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
}
