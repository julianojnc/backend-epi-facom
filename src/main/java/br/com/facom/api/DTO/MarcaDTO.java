package br.com.facom.api.DTO;

import jakarta.validation.constraints.NotNull;

public record MarcaDTO(
        Long id,
        @NotNull String nome
) {}
