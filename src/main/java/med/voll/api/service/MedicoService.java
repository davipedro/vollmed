package med.voll.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.dto.medico.RegistroMedicoDTO;
import med.voll.api.dto.medico.ResponseMedicoDTO;
import med.voll.api.dto.medico.UpdateMedicoDTO;
import med.voll.api.model.Endereco;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public void teste(){
        medicoRepository.findAll();
    }

    public Long cadastrarMedico(RegistroMedicoDTO dados) {
        Medico medico = new Medico(dados);

        medicoRepository.save(medico);

        return medico.getId();
    }

    public Page<ResponseMedicoDTO> buscarMedicos(Pageable paginacao) {
        return medicoRepository.findAll(paginacao)
            .map(medico -> new ResponseMedicoDTO(
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

    
    
}
