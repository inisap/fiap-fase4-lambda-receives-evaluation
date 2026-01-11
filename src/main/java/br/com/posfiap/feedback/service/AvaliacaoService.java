package br.com.posfiap.feedback.service;

import br.com.posfiap.feedback.dto.AvaliacaoRequest;
import br.com.posfiap.feedback.dto.NotificacaoUrgenciaRequest;
import br.com.posfiap.feedback.model.AvaliacaoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

@ApplicationScoped
public class AvaliacaoService {

    @Inject
    DynamoService dynamoService;

    @Inject
    SnsService snsService;

    public void processar(AvaliacaoRequest request) {

        AvaliacaoEntity entity =
                AvaliacaoEntity.from(
                        request.descricao,
                        request.nota
                );

        // Sempre grava no DynamoDB
        dynamoService.save(entity);

        // Publica no SNS se nota for 0 a 4
        if (request.nota <= 4) {

            NotificacaoUrgenciaRequest requestNotrificacao =
                    new NotificacaoUrgenciaRequest(
                            request.descricao,
                            "ALTA",
                            LocalDateTime.now());

            snsService.publish(
                    "Avaliação crítica recebida: " +
                            requestNotrificacao
            );
        }
    }
}
