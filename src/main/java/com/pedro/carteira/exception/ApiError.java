package com.pedro.carteira.exception;

import java.time.Instant;
import java.util.Map;

public record ApiError(
        Instant timestamp,
        int status,
        String mensagem,
        Map<String, String> campos
) {
}