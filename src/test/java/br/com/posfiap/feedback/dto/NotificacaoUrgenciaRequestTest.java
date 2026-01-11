package br.com.posfiap.feedback.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificacaoUrgenciaRequestTest {

    @Test
    void deveCriarNotificacaoUrgenciaComCamposPreenchidos() {
        // given
        String descricao = "Avaliação muito ruim";
        String urgencia = "ALTA";
        LocalDateTime dataEnvio = LocalDateTime.now();

        // when
        NotificacaoUrgenciaRequest request =
                new NotificacaoUrgenciaRequest(descricao, urgencia, dataEnvio);

        // then
        assertEquals(descricao, request.descricao);
        assertEquals(urgencia, request.urgencia);
        assertEquals(dataEnvio, request.dataEnvio);
    }

    @Test
    void devePermitirValoresNulosSeNecessario() {
        // when
        NotificacaoUrgenciaRequest request =
                new NotificacaoUrgenciaRequest(null, null, null);

        // then
        assertNull(request.descricao);
        assertNull(request.urgencia);
        assertNull(request.dataEnvio);
    }
}
