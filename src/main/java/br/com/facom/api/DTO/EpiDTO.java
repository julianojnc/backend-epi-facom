package br.com.facom.api.DTO;

import java.time.LocalDate;

import br.com.facom.api.Model.MarcaModel;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EpiDTO(
        Long id,
        @NotBlank(message = "O nome não deve ser nulo!") String nome,
        @NotNull String patrimonio,
        String serviceTag,
        String expressCode,
        String local,
        String setor,
        LocalDate dataCompra,
        LocalDate dataGarantia,
        MarcaModel idMarca
) {
}
