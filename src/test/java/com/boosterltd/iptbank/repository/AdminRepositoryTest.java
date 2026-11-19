package com.boosterltd.iptbank.repository;

import static com.boosterltd.lib.security.Security.getHashedVersion;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.boosterltd.iptbank.model.entity.AdminEntity;

@DataJpaTest @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AdminRepositoryTest {
    @Autowired private AdminRepository repository;
    private static final String username = "bruce_wayne";
    private static final String plainPassword = "i_am_batman";
    private static final String hashedPassword = getHashedVersion(plainPassword);

    @Test
    public void AdminRepository_Save() {
        Long entityId = null;
        final AdminEntity entity = new AdminEntity(
            entityId, hashedPassword, username
        );

        final AdminEntity savedEntity = repository.save(entity);
        entityId = savedEntity.getId();
        entity.setId(entityId);

        Assertions.assertThat(savedEntity).isEqualTo(entity);
        Assertions.assertThat(entityId).isGreaterThan(0);
    }

    @Test
    public void AdminRepository_FindById() {
        Long entityId = null;
        final AdminEntity entity = new AdminEntity(
            entityId, hashedPassword, username
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        final AdminEntity foundEntity = repository.findById(entityId).get();

        Assertions.assertThat(foundEntity).isEqualTo(entity);
    }
    @Test
    public void AdminRepository_FindAll() {
        Long entity1Id = null;
        Long entity2Id = null;
        final String username1 = username;
        final String username2 = "bruce_wayne_again";
        final AdminEntity entity1 = new AdminEntity(
            entity1Id, hashedPassword, username1
        );
        final AdminEntity entity2 = new AdminEntity(
            entity2Id, hashedPassword, username2
        );
        final List<AdminEntity> correctFoundEntityList = List.of(entity1, entity2);
        entity1Id = repository.save(entity1).getId();
        entity2Id = repository.save(entity2).getId();
        entity1.setId(entity1Id);
        entity2.setId(entity2Id);

        final List<AdminEntity> foundEntityList = repository.findAll();

        for (int i = 0; i < foundEntityList.size(); i += 1) {
            final AdminEntity foundEntity = foundEntityList.get(i);
            final AdminEntity correctFoundEntity = correctFoundEntityList.get(i);
            Assertions.assertThat(foundEntity).isEqualTo(correctFoundEntity);
        }
    }
    @Test
    public void AdminRepository_FindByUsername() {
        Long entityId = null;
        final AdminEntity entity = new AdminEntity(
            entityId, hashedPassword, username
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        final AdminEntity foundEntity = repository.findByUsername(entity.getUsername()).get();

        Assertions.assertThat(foundEntity).isEqualTo(entity);
    }

    @Test
    public void AdminRepository_Delete() {
        Long entityId = null;
        final AdminEntity entity = new AdminEntity(
            entityId, hashedPassword, username
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        repository.delete(entity);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
    @Test
    public void AdminRepository_DeleteById() {
        Long entityId = null;
        final AdminEntity entity = new AdminEntity(
            entityId, hashedPassword, username
        );
        entityId = repository.save(entity).getId();
        entity.setId(entityId);

        repository.deleteById(entityId);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
}
