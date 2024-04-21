package med.voll.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.consulta.RequestAgendamentoDTO;
import med.voll.api.dto.consulta.ResponseDadosConsultaDTO;
import med.voll.api.infra.exception.IdInexistenteException;
import med.voll.api.infra.exception.medico.EspecialidadeErradaException;
import med.voll.api.infra.exception.medico.MedicoNaoEncontradoException;
import med.voll.api.infra.exception.medico.MedicosIndisponiveisException;
import med.voll.api.infra.exception.paciente.PacienteNaoEncontradoException;
import med.voll.api.infra.validacoes.ValidadorAgendamentoConsultas;
import med.voll.api.model.Especialidade;
import med.voll.api.model.consulta.Consulta;
import med.voll.api.model.medico.Medico;
import med.voll.api.model.paciente.Paciente;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;

@Service
public class ConsultaService{

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadores;

    public List<Consulta> buscarConsultas(){
        return consultaRepository.findAll();
    }
    
    public ResponseDadosConsultaDTO agendar(Boolean medicoAleatorio, RequestAgendamentoDTO dados){
        
        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new PacienteNaoEncontradoException());

        Consulta consulta;

        if (medicoAleatorio) {
            consulta = consultaMedicoAleatorio(dados);
        } else {
            consulta = consultaMedicoEscolhido(dados);
        }
        
        consulta.setPaciente(paciente);

        validadores.forEach(v -> v.validar(dados));
        
        return new ResponseDadosConsultaDTO(
                consulta.getId(),
                consulta.getMedico(), 
                consulta.getPaciente(), 
                consulta.getData()
                );
    }

    private Consulta consultaMedicoEscolhido(RequestAgendamentoDTO dados) {
        Medico medico = medicoRepository.findById(dados.idMedico()).orElseThrow(() -> 
        new MedicoNaoEncontradoException());

        if (medico.getEspecialidade() != dados.especialidade()) {
            throw new EspecialidadeErradaException();
        }

        return new Consulta(null, medico, null, dados.data(), null);
    }

    private Consulta consultaMedicoAleatorio(RequestAgendamentoDTO dados) {
        
        Medico medico = escolherMedicoAleatorio(dados.data(), dados.especialidade());

        return new Consulta(null, medico, null, dados.data(), null);
    }

    private Medico escolherMedicoAleatorio(LocalDateTime data, Especialidade especialidade){
        return medicoRepository.buscarMedicoAleatorio(data, especialidade).orElseThrow(() -> new MedicosIndisponiveisException());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new IdInexistenteException();
        }
    
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
    
}
