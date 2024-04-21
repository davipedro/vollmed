package med.voll.api.dto.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        @JsonAlias("id")
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo) {
}
