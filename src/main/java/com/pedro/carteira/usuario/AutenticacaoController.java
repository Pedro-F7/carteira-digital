package com.pedro.carteira.usuario;

import com.pedro.carteira.infra.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> efetuarLogin(@RequestBody @Valid LoginRequest request) {
        // Cria o "crachá provisório" com e-mail e senha
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.senha());

        // O Spring verifica no banco de dados se a senha está correta
        var authentication = manager.authenticate(authenticationToken);

        // Se passar da linha acima, a senha está certa. Geramos o Token JWT
        var tokenJWT = tokenService.gerarToken(request.email());

        return ResponseEntity.ok(new TokenResponse(tokenJWT));
    }
}