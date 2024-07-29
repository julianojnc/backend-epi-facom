package br.com.facom.api.DTO;

import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Model.PerifericoModel;
import jakarta.validation.constraints.NotNull;

public record EpiPerifericoDTO(Long id, @NotNull EpiModel idEpi,@NotNull PerifericoModel idPeriferico) {
}
