package br.com.facom.api.Services;

import br.com.facom.api.DTO.EpiPerifericoDTO;
import br.com.facom.api.DTO.Mapper.EpiPerifericoMapper;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.Exceptions.ForbbidenHandler;
import br.com.facom.api.Exceptions.RegistroNaoEncontradoHendler;
import br.com.facom.api.Model.EpiPerifericoModel;
import br.com.facom.api.Model.PerifericoModel;
import br.com.facom.api.Repository.EpiPerifericoRepository;
import br.com.facom.api.Repository.PerifericoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
public class EpiPerifericoService {

    private final EpiPerifericoRepository repository;
    private final EpiPerifericoMapper mapper;
    private final PerifericoRepository perifericoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EpiPerifericoService(EpiPerifericoRepository repository, EpiPerifericoMapper mapper, PerifericoRepository perifericoRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.perifericoRepository = perifericoRepository;
    }

    public Pag<EpiPerifericoDTO> list(@RequestParam(name = "p") @PositiveOrZero int pageNumber, @RequestParam(name = "s") @Positive @Max(50) int pageSize, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                      @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<EpiPerifericoModel> page = repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<EpiPerifericoDTO> lista = page.stream().map(mapper::convertToDto).collect(Collectors.toList());
        return new Pag<>(lista, page.getTotalElements(), page.getTotalPages());
    }

    public EpiPerifericoDTO findById(@NotNull @Positive Long id) {
        return repository.findById(id).map(mapper::convertToDto).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public EpiPerifericoDTO create(@Valid EpiPerifericoDTO dto) {
        // Converte o DTO para a entidade EpiPerifericoModel e salva a relação entre EPI e Periférico
        EpiPerifericoModel epiPerifericoModel = mapper.convertToEntity(dto);

        // metodo privado de validação da vinculação e data do vínculo
        isVinculado(epiPerifericoModel, dto);

        return mapper.convertToDto(epiPerifericoModel);
    }


    public EpiPerifericoDTO update(@NotNull @Positive Long id, @NotNull @Valid EpiPerifericoDTO dto) {
        return repository.findById(id)
                .map(registroEncontrado -> {
                    if (registroEncontrado.getDataDesvinculacao() == null) {
                        registroEncontrado.setDataDesvinculacao(LocalDate.now());
                    }
                    Long perifericoId = dto.idPeriferico().getId();  //IdPeriferico() retorna um PerifericoModel
                    PerifericoModel periferico = perifericoRepository.findById(perifericoId)
                            .orElseThrow(() -> new RegistroNaoEncontradoHendler(perifericoId));

                    if (periferico.getIsVinculado() == 1) {
                        periferico.setIsVinculado(0);
                    }
                    // Atualiza o campo isVinculado do Periférico associado
                    perifericoRepository.save(periferico);
                    registroEncontrado.setRegistroDesvinculacao(dto.registroDesvinculacao());
                    return mapper.convertToDto(repository.save(registroEncontrado));
                }).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoHendler(id)));
    }

    /*
     * Método sem retorno para validação da vinculação do periferico e configuração da data da vinculação
     * @Param EpiPerifericoModel - classe entidade da epiPeriferico
     * @Param EpiPerifericoDTO - classe DTO de EpiPeriferico*/
    private void isVinculado(EpiPerifericoModel epiPerifericoModel, EpiPerifericoDTO dto) {

        if (epiPerifericoModel.getDataVinculacao() == null) {
            epiPerifericoModel.setDataVinculacao(LocalDate.now());
        }

        repository.save(epiPerifericoModel);

        // Obtém o Periférico associado a partir do EpiPerifericoModel salvo
        Long perifericoId = dto.idPeriferico().getId();  //IdPeriferico() retorna um PerifericoModel
        PerifericoModel periferico = perifericoRepository.findById(perifericoId)
                .orElseThrow(() -> new RegistroNaoEncontradoHendler(perifericoId));

        if (periferico.getIsVinculado() == 1) {
            throw new ForbbidenHandler("Este periférico já está vinculado a este ou outro Equipamento!");
        }
        // Atualiza o campo isVinculado do Periférico associado
        periferico.setIsVinculado(1);
        perifericoRepository.save(periferico);
    }
}
