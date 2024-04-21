package med.voll.api.infra.validacoes.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.dto.consulta.RequestAgendamentoDTO;
import med.voll.api.infra.exception.consulta.HorarioOcupadoException;
import med.voll.api.infra.validacoes.ValidadorAgendamentoConsultas;
import med.voll.api.repository.ConsultaRepository;

@Component
public class ValidadorDisponibilidadeMedico implements ValidadorAgendamentoConsultas{
    
    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(RequestAgendamentoDTO dados) {
        
        var existeConsulta = consultaRepository.existeConsultaNaData(dados.data(), dados.idMedico());

        if (existeConsulta) throw new HorarioOcupadoException();
    
    }
}
