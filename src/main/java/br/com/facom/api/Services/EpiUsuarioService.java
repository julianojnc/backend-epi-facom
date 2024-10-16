package br.com.facom.api.Services;

import br.com.facom.api.DTO.EpiUsuarioDTO;
import br.com.facom.api.DTO.Mapper.EpiUsuarioMapper;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.Exceptions.ForbbidenHandler;
import br.com.facom.api.Exceptions.RegistroNaoEncontradoHendler;
import br.com.facom.api.Model.EpiUsuarioModel;
import br.com.facom.api.Model.UsuarioModel;
import br.com.facom.api.Repository.EpiUsuarioRepository;
import br.com.facom.api.Repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class EpiUsuarioService {

    private final EpiUsuarioRepository repository;
    private final EpiUsuarioMapper mapper;

    private final UsuarioRepository usuarioRepository;

    public EpiUsuarioService(EpiUsuarioRepository repository, UsuarioRepository usuarioRepository, EpiUsuarioMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public Pag<EpiUsuarioDTO> list(@RequestParam(name = "p") @PositiveOrZero int pageNumber, @RequestParam(name = "s") @Positive @Max(50) int pageSize, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                   @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<EpiUsuarioModel> page = repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<EpiUsuarioDTO> lista = page.stream().map(mapper::convertToDto).collect(Collectors.toList());
        return new Pag<>(lista, page.getTotalElements(), page.getTotalPages());
    }

    public EpiUsuarioDTO findById(@NotNull @Positive Long id) {
        return repository.findById(id).map(mapper::convertToDto).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public EpiUsuarioDTO create(@Valid EpiUsuarioDTO dto) {
        EpiUsuarioModel epiUsuarioModel = mapper.convertToEntity(dto);

        if(epiUsuarioModel.getDataInicio()==null){
            epiUsuarioModel.setDataInicio(LocalDate.now());
        }

        isVinculado(dto);
        epiUsuarioModel = repository.save(epiUsuarioModel);

        return mapper.convertToDto(epiUsuarioModel);
    }

    public EpiUsuarioDTO update(@NotNull @Positive Long id, @NotNull @Valid EpiUsuarioDTO dto) {
        return repository.findById(id)
                .map(registroEncontrado -> {
                    Long usuarioId = dto.idUsuario().getId();
                    UsuarioModel usuario = usuarioRepository.findById(usuarioId).orElseThrow(()->new RegistroNaoEncontradoHendler(usuarioId));

                    if(usuario.getIsVinculado()==1){
                        usuario.setIsVinculado(0);
                    }

                    usuarioRepository.save(usuario);
                    BeanUtils.copyProperties(dto, registroEncontrado);
                    return mapper.convertToDto(repository.save(registroEncontrado));
                }).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoHendler(id)));
    }

    public void desvincula(@Positive Long id){

        EpiUsuarioModel epiUsuarioModel = repository.findById(id).orElseThrow(()-> new RegistroNaoEncontradoHendler(id));
        UsuarioModel usuario = epiUsuarioModel.getIdUsuario();

        if(epiUsuarioModel.getDataFim()==null){
            epiUsuarioModel.setDataFim(LocalDate.now());
        }

        if (usuario.getIsVinculado()==1){
            usuario.setIsVinculado(0);
        }

        usuarioRepository.save(usuario);
        repository.save(epiUsuarioModel);
    }

    private void isVinculado(EpiUsuarioDTO dto){
        Long usuarioID = dto.idUsuario().getId();
        UsuarioModel usuario = usuarioRepository.findById(usuarioID).orElseThrow(()-> new RegistroNaoEncontradoHendler(usuarioID));

        if(usuario.getIsVinculado()==1){
            throw new ForbbidenHandler("Este usuário já está vinculado a este ou outro Equipamento!");
        }

        usuario.setIsVinculado(1);
        usuarioRepository.save(usuario);
    }

}
