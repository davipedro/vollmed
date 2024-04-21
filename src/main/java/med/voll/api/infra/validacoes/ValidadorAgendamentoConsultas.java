package med.voll.api.infra.validacoes;

import med.voll.api.dto.consulta.RequestAgendamento;

public interface ValidadorAgendamentoConsultas {
    
    void validar(RequestAgendamento dados);
}
