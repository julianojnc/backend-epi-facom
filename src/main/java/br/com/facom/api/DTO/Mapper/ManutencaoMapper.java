package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.ManutencaoDTO;
import br.com.facom.api.Model.ManutencaoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ManutencaoMapper {

    public ManutencaoDTO convertToDto(ManutencaoModel model) {
        if (model == null) {
            return null;
        }
        return new ManutencaoDTO(
                model.getId(),
                model.getDescricao(),
                model.getValor(),
                model.getDataIniManutencao(),
                model.getDataRetManutencao(),
                model.getFileName(),
                model.getFileType(),
                model.getFilePath(),
                model.getIdEpi(),
                model.getIdPeriferico()
        );
    }

    public ManutencaoModel convertToEntity(ManutencaoDTO dto) {
        if (dto == null) {
            return null;
        }
        ManutencaoModel model = new ManutencaoModel();
        if (dto.id() != null) {
            model.setId(dto.id());
        }
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
