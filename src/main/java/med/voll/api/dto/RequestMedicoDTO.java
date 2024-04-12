package med.voll.api.dto;

import med.voll.api.model.Especialidade;

public record RequestMedicoDTO(String nome,
                                String email, 
                                String CRM, 
                                Especialidade especialidade,
                                DadosEndereco endereco) {
}
