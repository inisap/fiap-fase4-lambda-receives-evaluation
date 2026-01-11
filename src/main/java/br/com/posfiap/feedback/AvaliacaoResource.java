package br.com.posfiap.feedback;

import br.com.posfiap.feedback.dto.AvaliacaoRequest;
import br.com.posfiap.feedback.service.AvaliacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/avaliacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ALUNO")
public class AvaliacaoResource {

    @Inject
    AvaliacaoService avaliacaoService;

    @POST
    public Response avaliar(AvaliacaoRequest request) {

        avaliacaoService.processar(request);

        return Response.status(Response.Status.CREATED)
                .entity("{\"status\":\"AVALIACAO_REGISTRADA\"}")
                .build();
    }
}
