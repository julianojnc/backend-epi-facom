package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.EpiPerifericoDTO;
import br.com.facom.api.Model.EpiPerifericoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EpiPerifericoMapper {

    public EpiPerifericoDTO convertToDto(EpiPerifericoModel model) {
        if (model == null) {
            return null;
        }
        return new EpiPerifericoDTO(model.getId(), model.getIdEpi(), model.getIdPeriferico());
    }

    public EpiPerifericoModel convertToEntity(EpiPerifericoDTO dto) {
        if (dto == null) {
            return null;
        }
        EpiPerifericoModel model = new EpiPerifericoModel();
        if (dto.id() != null) {
            model.setId(dto.id());
        }
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
