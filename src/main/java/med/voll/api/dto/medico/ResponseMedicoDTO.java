package med.voll.api.dto.medico;

import med.voll.api.model.Especialidade;

public record ResponseMedicoDTO(String nome, 
                                String email, 
                                String CRM, 
                                Especialidade especialidade) {
}
