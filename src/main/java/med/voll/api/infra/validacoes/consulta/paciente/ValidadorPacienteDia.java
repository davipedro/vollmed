package med.voll.api.infra.validacoes.consulta.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.dto.consulta.RequestAgendamentoDTO;
import med.voll.api.infra.exception.paciente.PacienteDiaException;
import med.voll.api.infra.validacoes.ValidadorAgendamentoConsultas;
import med.voll.api.repository.ConsultaRepository;

@Component
public class ValidadorPacienteDia implements ValidadorAgendamentoConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(RequestAgendamentoDTO dados) {
        
        var primeiroHorario = dados.data().withHour(7);
        
        var ultimoHorario = dados.data().withHour(18);
        
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, 
        ultimoHorario);
        
        if (pacientePossuiOutraConsultaNoDia) {
            throw new PacienteDiaException();
        }
    }

}