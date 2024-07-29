package br.com.facom.api.DTO;

import jakarta.validation.constraints.NotNull;

public record UsuarioDTO(
        Long id,
        @NotNull String nome,
        String telContato,
        String email,
        int isVinculado
) {

}
