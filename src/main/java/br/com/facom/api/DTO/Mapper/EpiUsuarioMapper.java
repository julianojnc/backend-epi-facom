package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.EpiUsuarioDTO;
import br.com.facom.api.Model.EpiUsuarioModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EpiUsuarioMapper {

    public EpiUsuarioDTO convertToDto(EpiUsuarioModel model) {
        if (model == null) {
            return null;
        }
        return new EpiUsuarioDTO(model.getId(), model.getIdEpi(), model.getIdUsuario());
    }

    public EpiUsuarioModel convertToEntity(EpiUsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        EpiUsuarioModel model = new EpiUsuarioModel();
        if (dto.id() != null) {
            model.setId(dto.id());
        }
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
