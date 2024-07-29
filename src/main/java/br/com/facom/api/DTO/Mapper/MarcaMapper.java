package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.MarcaDTO;
import br.com.facom.api.Model.MarcaModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {

    public MarcaDTO convertToDto(MarcaModel model) {
        if (model == null) {
            return null;
        }
        return new MarcaDTO(model.getId(), model.getNome());
    }

    public MarcaModel convertToEntity(MarcaDTO dto) {
        if (dto == null) {
            return null;
        }
        MarcaModel model = new MarcaModel();
        if (dto.id() != null) {
            model.setId(dto.id());
        }
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
