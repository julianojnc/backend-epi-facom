package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.EpiDTO;
import br.com.facom.api.DTO.TokenDTO;
import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Model.TokenModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenDTO convertToDto(TokenModel model) {
        if (model == null) {
            return null;
        }

        return new TokenDTO(
                model.getId(),
                model.getToken());
    }

    public TokenModel convertToEntity(TokenDTO dto) {

        if (dto == null) {
            return null;
        }
        TokenModel model = new TokenModel();
        if (dto.id() != null) {
            model.setId(dto.id());
        }
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
