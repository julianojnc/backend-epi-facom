package br.com.facom.api.Services;

import br.com.facom.api.DTO.MarcaDTO;
import br.com.facom.api.DTO.Mapper.MarcaMapper;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.Exceptions.RegistroNaoEncontradoHendler;
import br.com.facom.api.Model.MarcaModel;
import br.com.facom.api.Repository.MarcaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class MarcaService {

    private final MarcaRepository repository;
    private final MarcaMapper mapper;

    public MarcaService(MarcaRepository repository, MarcaMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Pag<MarcaDTO> list(@RequestParam(name = "p") @PositiveOrZero int pageNumber, @RequestParam(name = "s") @Positive @Max(50) int pageSize, @RequestParam(value = "sortBy", defaultValue = "nome") String sortBy,
                              @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<MarcaModel> page = repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<MarcaDTO> lista = page.stream().map(mapper::convertToDto).collect(Collectors.toList());
        return new Pag<>(lista, page.getTotalElements(), page.getTotalPages());
    }

    public MarcaDTO findById(@NotNull @Positive Long id) {
        return repository.findById(id).map(mapper::convertToDto).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public MarcaDTO create(@Valid MarcaDTO dto) {
        return mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public MarcaDTO update(@NotNull @Positive Long id, @NotNull @Valid MarcaDTO dto) {
        return repository.findById(id)
                .map(registroEncontrado -> {
                    BeanUtils.copyProperties(dto, registroEncontrado);
                    return mapper.convertToDto(repository.save(registroEncontrado));
                }).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoHendler(id)));
    }

}
