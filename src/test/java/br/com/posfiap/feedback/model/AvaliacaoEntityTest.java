package br.com.posfiap.feedback.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoEntityTest {

    @Test
    void deveCriarAvaliacaoComPkSkCorretos() {
        // given
        String descricao = "Curso muito bom";
        int nota = 7;

        LocalDate hojeSp =
                LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        // when
        AvaliacaoEntity entity =
                AvaliacaoEntity.from(descricao, nota);

        // then
        assertNotNull(entity);

        // PK
        assertEquals("DATE#" + hojeSp, entity.getPk());

        // SK
        assertNotNull(entity.getSk());
        assertTrue(entity.getSk().startsWith("NOTA#" + nota + "#"));

        // valida UUID na SK
        String[] skParts = entity.getSk().split("#");
        assertEquals(3, skParts.length);
        assertDoesNotThrow(() ->
                UUID.fromString(skParts[2])
        );

        // campos de domínio
        assertEquals(descricao, entity.getDescricao());
        assertEquals(nota, entity.getNota());

        // data de criação
        assertNotNull(entity.getDataCriacao());
        assertTrue(
                entity.getDataCriacao().isBefore(Instant.now().plusSeconds(1))
        );
    }

    @Test
    void devePermitirSettersEGetters() {
        AvaliacaoEntity entity = new AvaliacaoEntity();

        Instant now = Instant.now();

        entity.setPk("PK_TESTE");
        entity.setSk("SK_TESTE");
        entity.setDescricao("Descricao teste");
        entity.setNota(10);
        entity.setDataCriacao(now);

        assertEquals("PK_TESTE", entity.getPk());
        assertEquals("SK_TESTE", entity.getSk());
        assertEquals("Descricao teste", entity.getDescricao());
        assertEquals(10, entity.getNota());
        assertEquals(now, entity.getDataCriacao());
    }

    @Test
    void deveGerarSkDiferenteParaCadaAvaliacao() {
        AvaliacaoEntity a1 =
                AvaliacaoEntity.from("Teste", 5);
        AvaliacaoEntity a2 =
                AvaliacaoEntity.from("Teste", 5);

        assertNotEquals(a1.getSk(), a2.getSk());
    }
}
