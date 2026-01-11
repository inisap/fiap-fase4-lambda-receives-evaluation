package br.com.posfiap.feedback.service;

import br.com.posfiap.feedback.dto.AvaliacaoRequest;
import br.com.posfiap.feedback.model.AvaliacaoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvaliacaoServiceTest {

    @Mock
    DynamoService dynamoService;

    @Mock
    SnsService snsService;

    @InjectMocks
    AvaliacaoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarNoDynamoQuandoNotaNaoForCritica() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest("Curso excelente", 8);

        // when
        service.processar(request);

        // then
        verify(dynamoService, times(1))
                .save(any(AvaliacaoEntity.class));

        verify(snsService, never())
                .publish(any());
    }

    @Test
    void deveSalvarNoDynamoEPublicarNoSnsQuandoNotaForCritica() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest("Curso ruim", 3);

        // when
        service.processar(request);

        // then
        verify(dynamoService, times(1))
                .save(any(AvaliacaoEntity.class));

        verify(snsService, times(1))
                .publish(argThat(msg ->
                        msg.contains("Avaliação crítica recebida")
                ));
    }

    @Test
    void deveTratarNotaLimiteComoCritica() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest("Curso mediano", 4);

        // when
        service.processar(request);

        // then
        verify(dynamoService, times(1))
                .save(any(AvaliacaoEntity.class));

        verify(snsService, times(1))
                .publish(any());
    }

    @Test
    void deveNaoPublicarSnsQuandoNotaForMaiorQueQuatro() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest("Curso bom", 5);

        // when
        service.processar(request);

        // then
        verify(dynamoService, times(1))
                .save(any(AvaliacaoEntity.class));

        verify(snsService, never())
                .publish(any());
    }
}
