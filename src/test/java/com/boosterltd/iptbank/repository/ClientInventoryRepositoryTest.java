package com.boosterltd.iptbank.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.boosterltd.iptbank.model.entity.ClientInventoryEntity;

@DataJpaTest @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClientInventoryRepositoryTest {
    @Autowired private ClientInventoryRepository repository;
    private static final Long clientId = 1L;
    private static final byte hotChocolateAmount = 1;
    private static final byte shekarestanSeriesEpisodeAmount = 2;
    private static final byte stillwaterSeriesEpisodeAmount = 3;
    private static final byte vpnSubscriptionAmount = 4;
 
    @Test
    public void ClientInventoryRepository_Save() {
        final ClientInventoryEntity entity = new ClientInventoryEntity(
            clientId, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );

        final ClientInventoryEntity savedEntity = repository.save(entity);
        entity.setClientId(clientId);

        Assertions.assertThat(savedEntity).isEqualTo(entity);
        Assertions.assertThat(clientId).isGreaterThan(0);
    }

    @Test
    public void ClientInventoryRepository_FindById() {
        final ClientInventoryEntity entity = new ClientInventoryEntity(
            clientId, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        repository.save(entity);

        final ClientInventoryEntity foundEntity = repository.findById(clientId).get();

        Assertions.assertThat(foundEntity).isEqualTo(entity);
    }
    @Test
    public void ClientInventoryRepository_FindAll() {
        final Long client1Id = clientId;
        final Long client2Id = 1L;
        final ClientInventoryEntity entity1 = new ClientInventoryEntity(
            client1Id, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        final ClientInventoryEntity entity2 = new ClientInventoryEntity(
            client2Id, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        final List<ClientInventoryEntity> correctFoundEntityList = List.of(entity1, entity2);
        repository.save(entity1);
        repository.save(entity2);
        
        final List<ClientInventoryEntity> foundEntityList = repository.findAll();

        for (int i = 0; i < foundEntityList.size(); i += 1) {
            final ClientInventoryEntity foundEntity = foundEntityList.get(i);
            final ClientInventoryEntity correctFoundEntity = correctFoundEntityList.get(i);
            Assertions.assertThat(foundEntity).isEqualTo(correctFoundEntity);
        }
    }

    @Test
    public void ClientInventoryRepository_Delete() {
        final ClientInventoryEntity entity = new ClientInventoryEntity(
            clientId, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        repository.save(entity);

        repository.delete(entity);

        Assertions.assertThat(repository.findById(clientId)).isEmpty();
    }
    @Test
    public void ClientInventoryRepository_DeleteById() {
        final ClientInventoryEntity entity = new ClientInventoryEntity(
            clientId, hotChocolateAmount, shekarestanSeriesEpisodeAmount,
            stillwaterSeriesEpisodeAmount, vpnSubscriptionAmount
        );
        repository.save(entity);

        repository.deleteById(clientId);

        Assertions.assertThat(repository.findById(clientId)).isEmpty();
    }
}
