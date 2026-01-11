package br.com.posfiap.feedback.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class SnsServiceTest {

    @Mock
    SnsClient snsClient;

    @InjectMocks
    SnsService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void devePublicarMensagemNoSns() {
        // given
        String mensagem = "Mensagem de teste";

        // when
        service.publish(mensagem);

        // then
        verify(snsClient, times(1))
                .publish(argThat((PublishRequest req) ->
                        mensagem.equals(req.message())
                ));
    }
}
