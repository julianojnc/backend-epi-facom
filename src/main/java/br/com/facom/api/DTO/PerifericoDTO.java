package br.com.facom.api.DTO;

import java.time.LocalDate;
import br.com.facom.api.Model.MarcaModel;
import jakarta.validation.constraints.NotNull;

public record PerifericoDTO(
        Long id,
        @NotNull String nome,
        String patrimonio,
        String serviceTag,
        String expressCode,
        LocalDate dataCompra,
        LocalDate dataGarantia,
        int isVinculado,
        String fileName,
        String fileType,
        String filePath,
        MarcaModel idMarca
) {}
