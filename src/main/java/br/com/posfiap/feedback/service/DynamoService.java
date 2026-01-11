package br.com.posfiap.feedback.service;

import br.com.posfiap.feedback.model.AvaliacaoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class DynamoService {

    private final DynamoDbTable<AvaliacaoEntity> table;

    @Inject
    public DynamoService(DynamoDbClient client) {
        DynamoDbEnhancedClient enhanced =
                DynamoDbEnhancedClient.builder()
                        .dynamoDbClient(client)
                        .build();

        this.table = enhanced.table(
                "avaliacoes",
                TableSchema.fromBean(AvaliacaoEntity.class)
        );
    }

    public void save(AvaliacaoEntity entity) {
        table.putItem(entity);
    }
}
