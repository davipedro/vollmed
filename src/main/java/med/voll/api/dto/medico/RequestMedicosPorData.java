package med.voll.api.dto.medico;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.model.Especialidade;

public record RequestMedicosPorData(@NotNull Especialidade especialidade, @NotNull @Future LocalDateTime data) {
    
}
