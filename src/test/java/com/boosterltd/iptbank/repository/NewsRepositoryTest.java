package com.boosterltd.iptbank.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.boosterltd.iptbank.model.entity.NewsEntity;

@DataJpaTest @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class NewsRepositoryTest {
    @Autowired private NewsRepository repository;
    private static final String entityId = "d:{3829-12-32},n={1}";
    private static final String content = "Batman is saving the city.";

    @Test
    public void NewsRepository_Save() {
        final NewsEntity entity = new NewsEntity(
            entityId, content
        );

        final NewsEntity savedEntity = repository.save(entity);

        Assertions.assertThat(savedEntity).isEqualTo(entity);
    }

    @Test
    public void NewsRepository_FindById() {
        final NewsEntity entity = new NewsEntity(
            entityId, content
        );
        repository.save(entity);

        final NewsEntity foundEntity = repository.findById(entityId).get();

        Assertions.assertThat(foundEntity).isEqualTo(entity);
    }
    @Test
    public void NewsRepository_FindAll() {
        final String entity1Id = entityId;
        final String entity2Id = "d:{3829-12-32},n={2}";
        final String content1 = content;
        final String content2 = "Batman is saving the city again.";
        final NewsEntity entity1 = new NewsEntity(
            entity1Id, content1
        );
        final NewsEntity entity2 = new NewsEntity(
            entity2Id, content2
        );
        final List<NewsEntity> correctFoundEntityList = List.of(entity1, entity2);
        repository.save(entity1);
        repository.save(entity2);

        final List<NewsEntity> foundEntityList = repository.findAll();
        
        for (int i = 0; i < foundEntityList.size(); i += 1) {
            final NewsEntity foundEntity = foundEntityList.get(i);
            final NewsEntity correctFoundEntity = correctFoundEntityList.get(i);
            Assertions.assertThat(foundEntity).isEqualTo(correctFoundEntity);
        }
    }

    @Test
    public void NewsRepository_Delete() {
        final NewsEntity entity = new NewsEntity(
            entityId, content
        );
        repository.save(entity);

        repository.delete(entity);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
    @Test
    public void NewsRepository_DeleteById() {
        final NewsEntity entity = new NewsEntity(
            entityId, content
        );
        repository.save(entity);

        repository.deleteById(entityId);

        Assertions.assertThat(repository.findById(entityId)).isEmpty();
    }
}
