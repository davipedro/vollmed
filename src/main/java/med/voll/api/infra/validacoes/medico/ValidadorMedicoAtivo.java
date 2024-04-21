package med.voll.api.infra.validacoes.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.dto.consulta.RequestAgendamento;
import med.voll.api.infra.exception.medico.MedicoInativoException;
import med.voll.api.infra.validacoes.ValidadorAgendamentoConsultas;
import med.voll.api.repository.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsultas {

    @Autowired
    private MedicoRepository repository;

    public void validar(RequestAgendamento dados) {
        //escolha do medico opcional
        if (dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo) {
            throw new MedicoInativoException("Consulta não pode ser agendada com médico excluído");
        }
    }

}
