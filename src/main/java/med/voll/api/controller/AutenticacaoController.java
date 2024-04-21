package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.dto.usuario.DadosAutenticacao;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.model.usuario.Usuario;
import med.voll.api.service.TokenService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    AuthenticationManager manager;

    private TokenService tokenService;
    
    public AutenticacaoController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<Object> efetuarLogin(@RequestBody DadosAutenticacao dados) {

        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var authentication = manager.authenticate(authToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
