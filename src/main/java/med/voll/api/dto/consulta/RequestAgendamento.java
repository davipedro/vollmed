package med.voll.api.dto.consulta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.model.Especialidade;

public record RequestAgendamento(@JsonAlias("id_medico") 
                                    Long idMedico,
                                    @NotNull
                                    Especialidade especialidade,
                                    @NotNull
                                    @JsonAlias("id_paciente")
                                    Long idPaciente,
                                    @NotNull
                                    @Future
                                    LocalDateTime data) {
}
