package med.voll.api.infra.validacoes.consulta.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.consulta.AntecedenciaException;
import med.voll.api.infra.validacoes.ValidadorCancelamentoDeConsulta;
import med.voll.api.repository.ConsultaRepository;

@Component
public class ValidadorHorarioAntecedenciaCancelamento implements ValidadorCancelamentoDeConsulta{
    
    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new AntecedenciaException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
