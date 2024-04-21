package med.voll.api.dto.consulta;

import java.time.LocalDateTime;

public record ResponseDadosConsulta(Long id,
                                       ResponseDadosMedicoResumo medico,
                                       ResponseDadosPacienteResumo paciente,
                                       LocalDateTime data) {
    
}
