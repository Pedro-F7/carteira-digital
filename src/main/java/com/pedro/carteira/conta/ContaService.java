package com.pedro.carteira.conta;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public void depositar(Long contaId, DepositoRequest request) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));

        conta.depositar(request.valor());
        // Depósitos não têm chave de idempotência neste exemplo, então passamos null
        transacaoRepository.save(new Transacao(conta, request.valor(), TipoTransacao.DEPOSITO, null));
    }

    public Map<String, BigDecimal> consultarSaldo(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));

        return Map.of("saldo", conta.getSaldo());
    }

    @Transactional
    public void transferir(Long contaOrigemId, TransferenciaRequest request) {
        if (transacaoRepository.existsByChaveIdempotencia(request.chaveIdempotencia())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Transferência já processada (Idempotência)");
        }

        if (contaOrigemId.equals(request.contaDestinoId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível transferir para a própria conta");
        }

        Conta origem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta de origem não encontrada"));

        Conta destino = contaRepository.findById(request.contaDestinoId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta de destino não encontrada"));

        origem.sacar(request.valor());
        destino.depositar(request.valor());

        transacaoRepository
                .save(new Transacao(origem, request.valor(), TipoTransacao.TRANSFERENCIA, request.chaveIdempotencia()));
        transacaoRepository.save(
                new Transacao(destino, request.valor(), TipoTransacao.TRANSFERENCIA, request.chaveIdempotencia()));
    }
}