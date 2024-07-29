package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.EpiDTO;
import br.com.facom.api.Model.EpiModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EpiMapper {

    public EpiDTO convertToDto(EpiModel model){
        if(model == null){
            return null;
        }
        return new EpiDTO(model.getId(), model.getNome(), model.getPatrimonio(), model.getLocal(), model.getSetor(), model.getDataCompra(), model.getDataGarantia(), model.getIdMarca());
    }

    public EpiModel convertToEntity(EpiDTO dto){

        if(dto == null){
            return null;
        }
        EpiModel model = new EpiModel();
        if (dto.id()!=null){
            model.setId(dto.id());
        }
        BeanUtils.copyProperties(dto, model);
        return model;
    }
}
