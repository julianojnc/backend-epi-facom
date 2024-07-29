package br.com.facom.api.DTO;

import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Model.PerifericoModel;
import br.com.facom.api.Model.UsuarioModel;
import jakarta.validation.constraints.NotNull;

public record EpiUsuarioDTO(
        Long id,
        @NotNull EpiModel idEpi,
        @NotNull UsuarioModel idUsuario
) {}
