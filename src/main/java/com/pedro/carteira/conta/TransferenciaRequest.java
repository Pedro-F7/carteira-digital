package com.pedro.carteira.conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferenciaRequest(
                @NotNull(message = "A conta de destino é obrigatória") Long contaDestinoId,

                @NotNull(message = "O valor é obrigatório") @Positive(message = "O valor da transferência deve ser maior que zero") BigDecimal valor,

                @NotBlank(message = "A chave de idempotência é obrigatória") String chaveIdempotencia) {
}