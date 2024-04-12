package med.voll.dto;

import med.voll.model.Especialidade;

public record RequestMedicoDTO(String nome,
                                String email, 
                                String CRM, 
                                Especialidade especialidade,
                                DadosEndereco endereco) {
}
