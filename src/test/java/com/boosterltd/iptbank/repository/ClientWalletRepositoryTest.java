package com.boosterltd.iptbank.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.boosterltd.iptbank.model.entity.ClientWalletEntity;

@DataJpaTest @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClientWalletRepositoryTest {
    @Autowired private ClientWalletRepository repository;
    private static final Long clientId = 1L;
    private static final byte irjAmount = 02;
    private static final short irrAmount = 19;

    @Test
    public void ClientWalletRepository_Save() {
        final ClientWalletEntity entity = new ClientWalletEntity(
            clientId, irjAmount, irrAmount
        );

        final ClientWalletEntity savedEntity = repository.save(entity);
        entity.setClientId(clientId);

        Assertions.assertThat(savedEntity).isEqualTo(entity);
        Assertions.assertThat(clientId).isGreaterThan(0);
    }

    @Test
    public void ClientWalletRepository_FindById() {
        final ClientWalletEntity entity = new ClientWalletEntity(
            clientId, irjAmount, irrAmount
        );
        repository.save(entity);

        final ClientWalletEntity foundEntity = repository.findById(clientId).get();

        Assertions.assertThat(foundEntity).isEqualTo(entity);
    }
    @Test
    public void ClientWalletRepository_FindAll() {
        final Long client1Id = clientId;
        final Long client2Id = 2L;
        final ClientWalletEntity entity1 = new ClientWalletEntity(
            client1Id, irjAmount, irrAmount
        );
        final ClientWalletEntity entity2 = new ClientWalletEntity(
            client2Id, irjAmount, irrAmount
        );
        final List<ClientWalletEntity> correctFoundEntityList = List.of(entity1, entity2);
        repository.save(entity1);
        repository.save(entity2);

        final List<ClientWalletEntity> foundEntityList = repository.findAll();

        for (int i = 0; i < foundEntityList.size(); i += 1) {
            final ClientWalletEntity foundEntity = foundEntityList.get(i);
            final ClientWalletEntity correctFoundEntity = correctFoundEntityList.get(i);
            Assertions.assertThat(foundEntity).isEqualTo(correctFoundEntity);
        }
    }

    @Test
    public void ClientWalletRepository_Delete() {
        final ClientWalletEntity entity = new ClientWalletEntity(
            clientId, irjAmount, irrAmount
        );
        repository.save(entity);

        repository.delete(entity);

        Assertions.assertThat(repository.findById(clientId)).isEmpty();
    }
    @Test
    public void ClientWalletRepository_DeleteById() {
        final ClientWalletEntity entity = new ClientWalletEntity(
            clientId, irjAmount, irrAmount
        );
        repository.save(entity);

        repository.deleteById(clientId);

        Assertions.assertThat(repository.findById(clientId)).isEmpty();
    }
}
