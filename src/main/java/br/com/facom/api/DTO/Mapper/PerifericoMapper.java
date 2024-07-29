package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.PerifericoDTO;
import br.com.facom.api.Model.PerifericoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PerifericoMapper {

    public PerifericoDTO convertToDto(PerifericoModel model) {
        if (model == null) {
            return null;
        }
        return new PerifericoDTO(
                model.getId(),
                model.getNome(),
                model.getExpressCode(),
                model.getDataCompra(),
                model.getDataVinculacao(),
                model.getIsVinculado(),
                model.getTempoVinculado(),
                model.getDataDesvinculacao(),
                model.getRegistroDesvinculacao(),
                model.getIdMarca()
        );
    }

    public PerifericoModel convertToEntity(PerifericoDTO dto) {
        if (dto == null) {
            return null;
        }
        PerifericoModel model = new PerifericoModel();
        if (dto.id() != null) {
            model.setId(dto.id());
        }
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
