package br.com.posfiap.feedback.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarAvaliacaoRequestValida() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest("Curso excelente", 9);

        // when
        Set<ConstraintViolation<AvaliacaoRequest>> violations =
                validator.validate(request);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveInvalidarDescricaoEmBranco() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest("", 5);

        // when
        Set<ConstraintViolation<AvaliacaoRequest>> violations =
                validator.validate(request);

        // then
        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("descricao"))
        );
    }

    @Test
    void deveInvalidarDescricaoNula() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest(null, 5);

        // when
        Set<ConstraintViolation<AvaliacaoRequest>> violations =
                validator.validate(request);

        // then
        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("descricao"))
        );
    }

    @Test
    void deveInvalidarNotaMenorQueZero() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest("Curso ruim", -1);

        // when
        Set<ConstraintViolation<AvaliacaoRequest>> violations =
                validator.validate(request);

        // then
        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("nota"))
        );
    }

    @Test
    void deveInvalidarNotaMaiorQueDez() {
        // given
        AvaliacaoRequest request =
                new AvaliacaoRequest("Curso excelente", 11);

        // when
        Set<ConstraintViolation<AvaliacaoRequest>> violations =
                validator.validate(request);

        // then
        assertFalse(violations.isEmpty());
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("nota"))
        );
    }

    @Test
    void deveAceitarNotasLimite() {
        AvaliacaoRequest notaZero =
                new AvaliacaoRequest("Nota mínima", 0);

        AvaliacaoRequest notaDez =
                new AvaliacaoRequest("Nota máxima", 10);

        assertTrue(validator.validate(notaZero).isEmpty());
        assertTrue(validator.validate(notaDez).isEmpty());
    }
}
