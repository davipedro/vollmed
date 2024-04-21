package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.consulta.RequestAgendamentoDTO;
import med.voll.api.service.ConsultaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    
    private ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }
    

    @PostMapping("/agendar")
    public ResponseEntity<Object> agendar(@RequestParam(defaultValue = "true") Boolean aleatorio, @RequestBody RequestAgendamentoDTO dados) {
        var consulta = consultaService.agendar(aleatorio, dados);
        
        return ResponseEntity.ok().body(consulta);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        consultaService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
    
}
