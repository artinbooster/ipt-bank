package com.boosterltd.iptbank.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.boosterltd.iptbank.model.entity.TradeTargetItemPriceEntity;

@DataJpaTest @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TradeTargetItemPriceRepositoryTest {
    @Autowired private TradeTargetItemPriceRepository repository;
    private static final Long tradeTargetItemId = 1L;
    private static final byte amountInPackage = 1;
    private static final byte priceIrjAmount = 02;
    private static final byte priceIrrAmount = 19;

    @Test
    public void TradeTargetItemPriceRepository_Save() {
        Long entityId = null;
        final TradeTargetItemPriceEntity entity = new TradeTargetItemPriceEntity(
            entityId, tradeTargetItemId, amountInPackage, priceIrjAmount, priceIrrAmount
        );

        final TradeTargetItemPriceEntity savedEntity = repository.save(entity);
        entityId = savedEntity.getId();
        entity.setId(entityId);

        Assertions.assertThat(savedEntity).isEqualTo(entity);
        Assertions.assertThat(entityId).isGreaterThan(0);
    }

    @Test
    public void TradeTargetItemPriceRepository_FindById() {
        Long entityId = null;
        final TradeTargetItemPriceEntity entity = new TradeTargetItemPriceEntity(
            entityId, tradeTargetItemId, amountInPackage, priceIrjAmount, priceIrrAmount
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        final TradeTargetItemPriceEntity foundEntity = repository.findById(entityId).get();

        Assertions.assertThat(foundEntity).isEqualTo(entity);
    }
    @Test
    public void TradeTargetItemPriceRepository_FindAll() {
        Long entity1Id = null;
        Long entity2Id = null;
        final TradeTargetItemPriceEntity entity1 = new TradeTargetItemPriceEntity(
            entity1Id, tradeTargetItemId, amountInPackage, priceIrjAmount, priceIrrAmount
        );
        final TradeTargetItemPriceEntity entity2 = new TradeTargetItemPriceEntity(
            entity2Id, tradeTargetItemId, amountInPackage, priceIrjAmount, priceIrrAmount
        );
        final List<TradeTargetItemPriceEntity> correctFoundEntityList = List.of(entity1, entity2);
        entity1Id = repository.save(entity1).getId();
        entity2Id = repository.save(entity2).getId();
        entity1.setId(entity1Id);
        entity2.setId(entity2Id);

        final List<TradeTargetItemPriceEntity> foundEntityList = repository.findAll();

        for (int i = 0; i < foundEntityList.size(); i += 1) {
            final TradeTargetItemPriceEntity foundEntity = foundEntityList.get(i);
            final TradeTargetItemPriceEntity correctFoundEntity = correctFoundEntityList.get(i);
            Assertions.assertThat(foundEntity).isEqualTo(correctFoundEntity);
        }
    }

    @Test
    public void TradeTargetItemPriceRepository_Delete() {
        Long entityId = null;
        final TradeTargetItemPriceEntity entity = new TradeTargetItemPriceEntity(
            entityId, tradeTargetItemId, amountInPackage, priceIrjAmount, priceIrrAmount
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        repository.delete(entity);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
    @Test
    public void TradeTargetItemPriceRepository_DeleteById() {
        Long entityId = null;
        final TradeTargetItemPriceEntity entity = new TradeTargetItemPriceEntity(
            entityId, tradeTargetItemId, amountInPackage, priceIrjAmount, priceIrrAmount
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        repository.deleteById(entityId);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
}
