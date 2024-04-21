package med.voll.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.dto.medico.RegistroMedicoDTO;
import med.voll.api.dto.medico.RequestMedicosPorData;
import med.voll.api.dto.medico.ResponseDadosMedicoDTO;
import med.voll.api.dto.medico.UpdateMedicoDTO;
import med.voll.api.infra.exception.IdInexistenteException;
import med.voll.api.infra.exception.medico.EspecialidadeNaoEncontrada;
import med.voll.api.model.Especialidade;
import med.voll.api.model.endereco.Endereco;
import med.voll.api.model.medico.Medico;
import med.voll.api.repository.MedicoRepository;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Long cadastrarMedico(RegistroMedicoDTO dados) {
        Medico medico = new Medico(dados);

        medicoRepository.save(medico);

        return medico.getId();
    }

    public Page<ResponseDadosMedicoDTO> buscarMedicos(Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao)
            .map(medico -> new ResponseDadosMedicoDTO(
                medico.getNome(), 
                medico.getEmail(), 
                medico.getCrm(), 
                medico.getEspecialidade()
            ));
    }

    public void atualizarDadosMedico(Long id, UpdateMedicoDTO medicoUpdate) {
        Medico medico = medicoRepository.findById(id).orElseThrow();

        if (medicoUpdate.nome() != null) {
            medico.setNome(medicoUpdate.nome());
        }
        if (medicoUpdate.telefone() != null) {
            medico.setTelefone(medicoUpdate.telefone());
        }
        if (medicoUpdate.endereco() != null) {
            Endereco endereco = new Endereco(medicoUpdate.endereco());

            medico.setEndereco(endereco);
        }
    }

    public void remocaoLogica(Long id) {
        var medico = medicoRepository.findById(id)
                        .orElseThrow(() -> new IdInexistenteException());
        
        medico.setAtivo(false);
    }

    public ResponseDadosMedicoDTO buscarMedicoPorId(Long id) {
        var medico = medicoRepository.findById(id)
                        .orElseThrow(() -> new IdInexistenteException());

        return new ResponseDadosMedicoDTO(
            medico.getNome(),
            medico.getEmail(), 
            medico.getCrm(),
            medico.getEspecialidade());
    }

    public List<String> buscarDisponiveis(RequestMedicosPorData dados) {

        var especialidadeValida = Especialidade.ehEspecialidade(dados.especialidade());

        if (!especialidadeValida) throw new EspecialidadeNaoEncontrada("Especialidade não encontrada!");

        List<Medico> medicosDisponiveis = medicoRepository.buscarDisponiveis(dados.especialidade(), dados.data());

        return medicosDisponiveis.stream()
                .map(Medico::getNome)
                .toList();
    }
}
