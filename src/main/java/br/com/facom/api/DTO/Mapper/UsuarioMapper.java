package br.com.facom.api.DTO.Mapper;

import br.com.facom.api.DTO.UsuarioDTO;
import br.com.facom.api.Model.UsuarioModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO convertToDto(UsuarioModel usuarioModel){
        if (usuarioModel==null){
            return null;
        }
        return new UsuarioDTO(usuarioModel.getId(), usuarioModel.getNome(), usuarioModel.getTelContato(), usuarioModel.getEmail(), usuarioModel.getIsVinculado());
    }

    public UsuarioModel convertToEntity(UsuarioDTO usuarioDTO){

        if (usuarioDTO==null){
            return null;
        }

        UsuarioModel usuarioModel = new UsuarioModel();
        if(usuarioDTO.id() != null){
            usuarioModel.setId(usuarioDTO.id());
        }
        BeanUtils.copyProperties(usuarioDTO, usuarioModel); //setNome, email e etc
        return usuarioModel;
    }


}
