package med.voll.api.dto.medico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import med.voll.api.dto.DadosEndereco;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateMedicoDTO(String nome,
                              String telefone, 
                              DadosEndereco endereco) {
    
}
