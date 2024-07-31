package br.com.facom.api.Services;

import br.com.facom.api.DTO.PerifericoDTO;
import br.com.facom.api.DTO.Mapper.PerifericoMapper;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.Exceptions.RegistroNaoEncontradoHendler;
import br.com.facom.api.Model.PerifericoModel;
import br.com.facom.api.Repository.PerifericoRepository;
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
public class PerifericoService {

    private final PerifericoRepository repository;
    private final PerifericoMapper mapper;

    public PerifericoService(PerifericoRepository repository, PerifericoMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }


    public Pag<PerifericoDTO> list(@RequestParam(name = "p") @PositiveOrZero int pageNumber, @RequestParam(name = "s") @Positive @Max(50) int pageSize, @RequestParam(value = "sortBy", defaultValue = "nome") String sortBy,
                                   @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<PerifericoModel> page = repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<PerifericoDTO> lista = page.stream().map(mapper::convertToDto).collect(Collectors.toList());
        return new Pag<>(lista, page.getTotalElements(), page.getTotalPages());
    }

    public PerifericoDTO findById(@NotNull @Positive Long id) {
        return repository.findById(id).map(mapper::convertToDto).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public PerifericoDTO create(@Valid PerifericoDTO dto) {
        return mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public PerifericoDTO update(@NotNull @Positive Long id, @NotNull @Valid PerifericoDTO dto) {
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
