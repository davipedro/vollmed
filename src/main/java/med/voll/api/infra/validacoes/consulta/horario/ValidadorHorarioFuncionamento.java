package med.voll.api.infra.validacoes.consulta.horario;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.dto.consulta.RequestAgendamentoDTO;
import med.voll.api.infra.exception.consulta.ConsultaHorarioException;
import med.voll.api.infra.validacoes.ValidadorAgendamentoConsultas;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsultas{
    
    @Override
    public void validar(RequestAgendamentoDTO dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;
        
        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
            throw new ConsultaHorarioException();
        }

    }   
}