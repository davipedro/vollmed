package med.voll.api.infra.validacoes;

import med.voll.api.dto.consulta.RequestAgendamentoDTO;

public interface ValidadorAgendamentoConsultas {
    
    void validar(RequestAgendamentoDTO dados);
}
