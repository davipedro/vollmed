package med.voll.api.dto.consulta;

import med.voll.api.model.Especialidade;

public record ResponseDadosMedicoResumo(Long id,
                                        String nome,
                                        Especialidade especialidade,
                                        String crm) {
}
