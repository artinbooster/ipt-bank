package com.boosterltd.iptbank.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.boosterltd.iptbank.model.entity.TradeTargetItemEntity;

@DataJpaTest @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TradeTargetItemRepositoryTest {
    @Autowired private TradeTargetItemRepository repository;    
    private static final String name = "BIRTHDAY_CAKE";
    
    @Test
    public void TradeTargetItemRepository_Save() {
        Long entityId = null;
        final TradeTargetItemEntity entity = new TradeTargetItemEntity(
            entityId, name
        );

        final TradeTargetItemEntity savedEntity = repository.save(entity);
        entityId = savedEntity.getId();
        entity.setId(entityId);

        Assertions.assertThat(savedEntity).isEqualTo(entity);
        Assertions.assertThat(entityId).isGreaterThan(0);
    }

    @Test
    public void TradeTargetItemRepository_FindById() {
        Long entityId = null;
        final TradeTargetItemEntity entity = new TradeTargetItemEntity(
            entityId, name
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        final TradeTargetItemEntity foundEntity = repository.findById(entityId).get();

        Assertions.assertThat(foundEntity).isEqualTo(entity);
    }
    @Test
    public void TradeTargetItemRepository_FindAll() {
        Long entity1Id = null;
        Long entity2Id = null;
        final String name1 = name;
        final String name2 = "BIRTHDAY_CAKE_AGAIN";
        final TradeTargetItemEntity entity1 = new TradeTargetItemEntity(
            entity1Id, name1
        );
        final TradeTargetItemEntity entity2 = new TradeTargetItemEntity(
            entity2Id, name2
        );
        final List<TradeTargetItemEntity> correctFoundEntityList = List.of(entity1, entity2);
        entity1Id = repository.save(entity1).getId();
        entity2Id = repository.save(entity2).getId();
        entity1.setId(entity1Id);
        entity2.setId(entity2Id);

        final List<TradeTargetItemEntity> foundEntityList = repository.findAll();

        for (int i = 0; i < foundEntityList.size(); i += 1) {
            final TradeTargetItemEntity foundEntity = foundEntityList.get(i);
            final TradeTargetItemEntity correctFoundEntity = correctFoundEntityList.get(i);
            Assertions.assertThat(foundEntity).isEqualTo(correctFoundEntity);
        }
    }

    @Test
    public void TradeTargetItemRepository_Delete() {
        Long entityId = null;
        final TradeTargetItemEntity entity = new TradeTargetItemEntity(
            entityId, name
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        repository.delete(entity);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
    @Test
    public void TradeTargetItemRepository_DeleteById() {
        Long entityId = null;
        final TradeTargetItemEntity entity = new TradeTargetItemEntity(
            entityId, name
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        repository.deleteById(entityId);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
}
