package br.com.posfiap.feedback.service;

import br.com.posfiap.feedback.model.AvaliacaoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@ApplicationScoped
public class DynamoService {

    private final DynamoDbTable<AvaliacaoEntity> table;

    /**
     * Construtor de produção (Quarkus injeta o Enhanced Client)
     */
    @Inject
    public DynamoService(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table(
                "avaliacoes",
                TableSchema.fromBean(AvaliacaoEntity.class)
        );
    }

    /**
     * Construtor visível para testes unitários
     */
    DynamoService(DynamoDbTable<AvaliacaoEntity> table) {
        this.table = table;
    }

    public void save(AvaliacaoEntity entity) {
        table.putItem(entity);
    }
}
