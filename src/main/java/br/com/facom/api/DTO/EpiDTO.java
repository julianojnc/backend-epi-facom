package br.com.facom.api.DTO;

import java.time.LocalDate;
import br.com.facom.api.Model.MarcaModel;
import jakarta.validation.constraints.NotNull;

public record EpiDTO(
        Long id,
        @NotNull String nome,
        @NotNull String patrimonio,
        String local,
        String setor,
        LocalDate dataCompra,
        LocalDate dataGarantia,
        MarcaModel idMarca
) {}
