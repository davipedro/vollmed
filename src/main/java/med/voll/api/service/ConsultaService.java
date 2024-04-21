package med.voll.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.consulta.RequestAgendamento;
import med.voll.api.dto.consulta.ResponseDadosConsulta;
import med.voll.api.dto.consulta.ResponseDadosMedicoResumo;
import med.voll.api.dto.consulta.ResponseDadosPacienteResumo;
import med.voll.api.infra.exception.IdInexistenteException;
import med.voll.api.infra.exception.medico.EspecialidadeErradaException;
import med.voll.api.infra.exception.medico.MedicoNaoEncontradoException;
import med.voll.api.infra.exception.medico.MedicosIndisponiveisException;
import med.voll.api.infra.exception.paciente.PacienteNaoEncontradoException;
import med.voll.api.infra.validacoes.ValidadorAgendamentoConsultas;
import med.voll.api.infra.validacoes.ValidadorCancelamentoDeConsulta;
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
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadores;

    public List<Consulta> buscarConsultas(){
        return consultaRepository.findAll();
    }
    
    public ResponseDadosConsulta agendar(Boolean medicoAleatorio, RequestAgendamento dados){
        
        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new PacienteNaoEncontradoException("Paciente de id:" + dados.idPaciente() +" não encontrado"));

        Consulta consulta;

        if (medicoAleatorio) {
            consulta = buscaMedicoAleatorio(dados);
        } else {
            consulta = buscaMedicoEscolhido(dados);
        }
        
        consulta.setPaciente(paciente);
        
        //chama todas as validações que utilizam a interface ValidadorAgendamentoConsultas
        validadores.forEach(v -> v.validar(dados));
        
        Consulta consultaSalva = consultaRepository.save(consulta);

        var medicoResumo = new ResponseDadosMedicoResumo(
            consultaSalva.getMedico().getId(),
            consultaSalva.getMedico().getNome(),
            consultaSalva.getMedico().getEspecialidade(),
            consultaSalva.getMedico().getCrm()
        );

        var pacienteResumo = new ResponseDadosPacienteResumo(
            consultaSalva.getPaciente().getId(),
            consultaSalva.getPaciente().getNome(),
            consultaSalva.getPaciente().getCpf(),
            consultaSalva.getPaciente().getTelefone()
        );

        return new ResponseDadosConsulta(
                consultaSalva.getId(),
                medicoResumo,
                pacienteResumo,
                consulta.getData()
                );
    }

    private Consulta buscaMedicoEscolhido(RequestAgendamento dados) {
        if (dados.idMedico() == null) throw new IdInexistenteException("Id do médico inválido");

        Medico medico = medicoRepository.findById(dados.idMedico()).orElseThrow(() -> 
        new MedicoNaoEncontradoException("Médico com id: " + dados.idMedico() + "não encontrado"));

        if (medico.getEspecialidade() != dados.especialidade()) {
            throw new EspecialidadeErradaException("O médico não condiz com a especialidade fornecida");
        }

        return new Consulta(null, medico, null, dados.data(), null);
    }

    private Consulta buscaMedicoAleatorio(RequestAgendamento dados) {
        if (dados.especialidade() == null) {
            throw new EspecialidadeErradaException("A especialidade deve ser fornecida");
        }

        Medico medico = escolherMedicoAleatorio(dados.data(), dados.especialidade());

        return new Consulta(null, medico, null, dados.data(), null);
    }

    private Medico escolherMedicoAleatorio(LocalDateTime data, Especialidade especialidade){
        return medicoRepository.buscarMedicoAleatorio(data, especialidade).orElseThrow(() -> new MedicosIndisponiveisException("Não há médicos disponíveis"));
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new IdInexistenteException();
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));
    
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
    
}
