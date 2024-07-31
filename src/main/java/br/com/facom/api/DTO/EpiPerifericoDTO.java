package br.com.facom.api.DTO;

import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Model.PerifericoModel;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EpiPerifericoDTO(
        Long id,
        @NotNull EpiModel idEpi,
        @NotNull PerifericoModel idPeriferico,
        LocalDate dataVinculacao,
        LocalDate dataDesvinculacao,
        String registroDesvinculacao) {
}
