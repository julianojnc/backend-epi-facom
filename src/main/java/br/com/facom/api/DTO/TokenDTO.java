package br.com.facom.api.DTO;

import jakarta.validation.constraints.NotNull;

public record TokenDTO (
    Long id,
    @NotNull String token
    ){}
