package med.voll.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.medico.RegistroMedicoDTO;
import med.voll.api.dto.medico.RequestMedicosPorData;
import med.voll.api.dto.medico.ResponseDadosMedicoDTO;
import med.voll.api.dto.medico.UpdateMedicoDTO;
import med.voll.api.service.MedicoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private static final String URI_BASE = "localhost:8080/medicos";
    
    @Autowired
    private MedicoService medicoService;

    @PostMapping("/medico")
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid RegistroMedicoDTO dados){
        Long medicoId = medicoService.cadastrarMedico(dados);

        String uri = URI_BASE + "/medico" + medicoId;

        return ResponseEntity.created(URI.create(uri)).build();
    }

    @GetMapping
    public ResponseEntity<Page<ResponseDadosMedicoDTO>> getMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok().body(medicoService.buscarMedicos(paginacao));
    }

    @GetMapping("/data")
    public ResponseEntity<List<String>> getMedicosPorData(@RequestBody RequestMedicosPorData dados) {
        List<String> disponibilidadeMedicos = medicoService.buscarDisponiveis(dados);

        return ResponseEntity.ok().body(disponibilidadeMedicos);
    }
    

    @GetMapping("{id}")
    public ResponseEntity<ResponseDadosMedicoDTO> getMedicosPorId(@PathVariable Long id) {
        var medico = medicoService.buscarMedicoPorId(id);
        
        return ResponseEntity.ok().body(medico);
    }
    

    @PutMapping("/medico/{id}")
    @Transactional
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody UpdateMedicoDTO medicoUpdate) {
        medicoService.atualizarDadosMedico(id, medicoUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/medico/{id}")
    @Transactional
    public ResponseEntity<Object> remocaoLogica(@PathVariable Long id){
        medicoService.remocaoLogica(id);
        return ResponseEntity.noContent().build();
    }
    
}
