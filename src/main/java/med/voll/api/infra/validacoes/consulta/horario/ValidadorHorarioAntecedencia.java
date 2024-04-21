package med.voll.api.infra.validacoes.consulta.horario;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.dto.consulta.RequestAgendamento;
import med.voll.api.infra.exception.consulta.AntecedenciaException;
import med.voll.api.infra.validacoes.ValidadorAgendamentoConsultas;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsultas{
    
    @Override
    public void validar(RequestAgendamento dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaMinutos = Duration.between(agora, dataConsulta).toMinutes();
    
        if (diferencaMinutos < 30) {
            throw new AntecedenciaException("A consulta de ser agendada com antecedencia minima de 30 minutos");
        }
    
    }
}