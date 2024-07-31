package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.EpiPerifericoDTO;
import br.com.facom.api.Model.EpiPerifericoModel;
import br.com.facom.api.Model.PerifericoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EpiPerifericoMapper {

    public EpiPerifericoDTO convertToDto(EpiPerifericoModel model) {
        if (model == null) {
            return null;
        }
        return new EpiPerifericoDTO(
                model.getId(),
                model.getIdEpi(),
                model.getIdPeriferico(),
                model.getDataVinculacao(),
                model.getDataDesvinculacao(),
                model.getRegistroDesvinculacao());
    }

    public EpiPerifericoModel convertToEntity(EpiPerifericoDTO dto) {
        if (dto == null) {
            return null;
        }
        EpiPerifericoModel model = new EpiPerifericoModel();
        if (dto.id() != null) {
            model.setId(dto.id());
        }

        model.setIdPeriferico(dto.idPeriferico());
        model.setIdEpi(dto.idEpi());
        model.setDataVinculacao(dto.dataVinculacao());
        model.setDataDesvinculacao(dto.dataDesvinculacao());
        model.setRegistroDesvinculacao(dto.registroDesvinculacao());
        return model;
    }


}
