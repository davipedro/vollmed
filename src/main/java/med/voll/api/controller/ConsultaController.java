package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.consulta.RequestAgendamento;
import med.voll.api.dto.consulta.ResponseDadosConsulta;
import med.voll.api.service.ConsultaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    
    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/agendar")
    public ResponseEntity<ResponseDadosConsulta> agendar(@RequestParam(defaultValue = "true") Boolean aleatorio, @RequestBody RequestAgendamento dados) {
        var consulta = consultaService.agendar(aleatorio, dados);
        
        return ResponseEntity.ok().body(consulta);
    }

    @Transactional
    @DeleteMapping("/consulta")
    public ResponseEntity<Object> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        consultaService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
    
}
