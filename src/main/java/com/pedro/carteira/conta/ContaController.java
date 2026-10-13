package com.pedro.carteira.conta;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    @Autowired
    private EntityManager entityManager;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestBody @Valid DepositoRequest request) {
        contaService.depositar(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Map<String, BigDecimal>> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.consultarSaldo(id));
    }

    @PostMapping("/{id}/transferencia")
    public ResponseEntity<Void> transferir(@PathVariable Long id, @RequestBody @Valid TransferenciaRequest request) {
        contaService.transferir(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Rota para forçar a criação da conta no banco e finalizar o teste
    @PostMapping("/criar-forcado")
    @Transactional
    public ResponseEntity<Void> criarContaForcada() {
        entityManager.createNativeQuery("INSERT INTO contas (saldo, usuario_id, versao) VALUES (0, 1, 0)")
                .executeUpdate();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}