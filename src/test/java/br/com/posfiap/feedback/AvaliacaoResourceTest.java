package br.com.posfiap.feedback;

import br.com.posfiap.feedback.dto.AvaliacaoRequest;
import br.com.posfiap.feedback.service.AvaliacaoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
class AvaliacaoResourceTest {

    @InjectMock
    AvaliacaoService avaliacaoService;

    @Test
    @TestSecurity(user = "aluno_teste", roles = {"ALUNO"})
    void deveRegistrarAvaliacaoComSucesso() {

        AvaliacaoRequest request =
                new AvaliacaoRequest("Curso excelente", 9);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/avaliacao")
                .then()
                .statusCode(201)
                .body("status", equalTo("AVALIACAO_REGISTRADA"));

        verify(avaliacaoService, times(1))
                .processar(any(AvaliacaoRequest.class));
    }
}
