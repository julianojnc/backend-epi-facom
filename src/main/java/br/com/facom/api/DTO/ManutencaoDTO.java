package br.com.facom.api.DTO;

import java.time.LocalDate;
import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Model.PerifericoModel;
import jakarta.validation.constraints.NotNull;

public record ManutencaoDTO(
        Long id,
        @NotNull String descricao,
        float valor,
        LocalDate dataIniManutencao,
        LocalDate dataRetManutencao,
        EpiModel idEpi,
        PerifericoModel idPeriferico
) {}
