package med.voll.api.dto.consulta;

import java.time.LocalDateTime;

import med.voll.api.model.medico.Medico;
import med.voll.api.model.paciente.Paciente;

public record ResponseDadosConsultaDTO(Long id,
                                        Medico medico,
                                        Paciente paciente,
                                        LocalDateTime data) {
    
}
