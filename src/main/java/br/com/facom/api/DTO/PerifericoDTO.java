package br.com.facom.api.DTO;

import java.time.LocalDate;
import br.com.facom.api.Model.MarcaModel;
import jakarta.validation.constraints.NotNull;

public record PerifericoDTO(
        Long id,
        @NotNull String nome,
        String expressCode,
        LocalDate dataCompra,
        @NotNull LocalDate dataVinculacao,
        int isVinculado,
        int tempoVinculado,
        LocalDate dataDesvinculacao,
        String registroDesvinculacao,
        MarcaModel idMarca
) {}
