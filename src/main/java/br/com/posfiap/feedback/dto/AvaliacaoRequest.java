package br.com.posfiap.feedback.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AvaliacaoRequest {

    @NotBlank
    public String descricao;

    @Min(0)
    @Max(10)
    public int nota;
}
