package br.com.facom.api.services;

import br.com.facom.api.DTO.Mapper.UsuarioMapper;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.DTO.UsuarioDTO;
import br.com.facom.api.Exceptions.RegistroNaoEncontradoHendler;
import br.com.facom.api.Model.UsuarioModel;
import br.com.facom.api.Repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public Pag<UsuarioDTO> list(@RequestParam(name = "p") @PositiveOrZero int pageNumber, @RequestParam(name = "s") @Positive @Max(50) int pageSize, @RequestParam(value = "sortBy", defaultValue = "nome") String sortBy,
                    @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<UsuarioModel> page = usuarioRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<UsuarioDTO> usuarioDTOList = page.stream().map(usuarioMapper::convertToDto).collect(Collectors.toList());
        return new Pag<>(usuarioDTOList, page.getTotalElements(), page.getTotalPages());
    }

    public UsuarioDTO findById(@NotNull @Positive Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::convertToDto).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public UsuarioDTO create(@NotNull @Valid UsuarioDTO usuarioDTO) {
        return usuarioMapper.convertToDto(usuarioRepository.save(usuarioMapper.convertToEntity(usuarioDTO)));
    }

    public UsuarioDTO update(@NotNull @Positive Long id, @NotNull @Valid UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id)
                .map(registroEncontrado -> {
                    BeanUtils.copyProperties(usuarioDTO, registroEncontrado);
                    return usuarioMapper.convertToDto(usuarioRepository.save(registroEncontrado));
                }).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public void delete(@NotNull @Positive Long id) {
        usuarioRepository.delete(usuarioRepository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoHendler(id)));
    }
}
