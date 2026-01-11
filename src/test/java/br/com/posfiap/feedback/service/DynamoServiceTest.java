package br.com.posfiap.feedback.service;

import br.com.posfiap.feedback.model.AvaliacaoEntity;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Teste unitário do DynamoService
 */
class DynamoServiceTest {

    @Test
    void deveChamarPutItemAoSalvar() {

        DynamoDbTable<AvaliacaoEntity> table =
                mock(DynamoDbTable.class);

        DynamoService service =
                new DynamoService(table);

        AvaliacaoEntity entity =
                new AvaliacaoEntity();


        service.save(entity);

        verify(table).putItem(entity);
    }

}
