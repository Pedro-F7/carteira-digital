package com.pedro.carteira.conta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoTransacao tipo;

    @Column(name = "chave_idempotencia", unique = true)
    private String chaveIdempotencia;

    @Column(name = "realizada_em", nullable = false, updatable = false)
    private Instant realizadaEm;

    protected Transacao() {
    }

    public Transacao(Conta conta, BigDecimal valor, TipoTransacao tipo, String chaveIdempotencia) {
        this.conta = conta;
        this.valor = valor;
        this.tipo = tipo;
        this.chaveIdempotencia = chaveIdempotencia;
        this.realizadaEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Conta getConta() {
        return conta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public String getChaveIdempotencia() {
        return chaveIdempotencia;
    }

    public Instant getRealizadaEm() {
        return realizadaEm;
    }
}