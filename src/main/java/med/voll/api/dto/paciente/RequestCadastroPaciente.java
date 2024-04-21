package med.voll.api.dto.paciente;

import med.voll.api.dto.DadosEndereco;

public record RequestCadastroPaciente(String nome,
                                      String email,
                                      String telefone,
                                      String cpf,
                                      DadosEndereco endereco) {
}