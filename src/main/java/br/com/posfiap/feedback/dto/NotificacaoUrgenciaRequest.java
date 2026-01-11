package br.com.posfiap.feedback.dto;

import java.time.LocalDateTime;

public class NotificacaoUrgenciaRequest {

    public String descricao;

    public String urgencia;

    public LocalDateTime dataEnvio;

    public NotificacaoUrgenciaRequest(String descricao, String urgencia, LocalDateTime dataEnvio){
        this.descricao = descricao;
        this.urgencia = urgencia;
        this.dataEnvio = dataEnvio;
    }
}
