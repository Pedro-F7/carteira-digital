package com.pedro.carteira.conta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositoRequest(
        @NotNull(message = "O valor é obrigatório") @Positive(message = "O valor do depósito deve ser maior que zero") BigDecimal valor) {
}