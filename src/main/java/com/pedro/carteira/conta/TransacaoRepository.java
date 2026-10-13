package com.pedro.carteira.conta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    boolean existsByChaveIdempotencia(String chaveIdempotencia);
}