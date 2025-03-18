package br.com.facom.api.Services;

import br.com.facom.api.DTO.EpiDTO;
import br.com.facom.api.DTO.Mapper.EpiMapper;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.Exceptions.ForbbidenHandler;
import br.com.facom.api.Exceptions.RegistroNaoEncontradoHendler;
import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Repository.EpiRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Validated
@Service
public class EpiService {

    private final EpiRepository repository;
    private final EpiMapper mapper;
    private final SimpMessagingTemplate messagingTemplate; // NOTIFICACAO

    @Autowired
    public EpiService(EpiRepository repository, EpiMapper mapper, SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.messagingTemplate = messagingTemplate;
    }

    // PAGINACAO
    public Pag<EpiDTO> list(@RequestParam(name = "p") @PositiveOrZero int pageNumber,
            @RequestParam(name = "s") @Positive @Max(50) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "nome") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Page<EpiModel> page = repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<EpiDTO> lista = page.stream().map(mapper::convertToDto).collect(Collectors.toList());
        return new Pag<>(lista, page.getTotalElements(), page.getTotalPages());
    }

    public EpiDTO findById(@NotNull @Positive Long id) {
        return repository.findById(id).map(mapper::convertToDto)
                .orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public EpiDTO create(@Valid EpiDTO dto) {
        return mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));

    }

    public EpiDTO update(@NotNull @Positive Long id, @NotNull @Valid EpiDTO dto) {
        return repository.findById(id)
                .map(registroEncontrado -> {
                    BeanUtils.copyProperties(dto, registroEncontrado);
                    return mapper.convertToDto(repository.save(registroEncontrado));
                }).orElseThrow(() -> new RegistroNaoEncontradoHendler(id));
    }

    public void delete(@NotNull @Positive Long id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoHendler(id)));
    }

    // UPLOAD DE ARQUIVOS
    public String storeFile(Long id, MultipartFile file) throws IOException {
        EpiModel epi = repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoHendler(id));

        // DIRETORIO DE SALVAMENTO DOS ARQUVIOS
        String uploadDir = "C:/uploads/epi-files/";
        String fileName = epi.getId() + "_" + file.getOriginalFilename(); // RENOMEANDO O ARQUIVO PARA O MESMO TER O NOME UNICO
        Path filePath = Paths.get(uploadDir + fileName);

        // SALVANDO O ARQUIVO NO DIRETORIO
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // ATUALIZANDO CAMPOS DO EPI COM OS DADOS DO ARQUIVO
        epi.setFileName(fileName);
        epi.setFileType(file.getContentType());
        epi.setFilePath(filePath.toString());

        // SALVANDO EPI COM OS DADOS ATUALIZADOS
        EpiModel updatedEpi = repository.save(epi);

        return fileName;
    }

    // BUSCAR EQUIPAMENTOS COM DATA DE GARANTIA IGUAL A DATA ATUAL
    public List<EpiModel> findEquipamentosComGarantiaExpirada() {
        LocalDate hoje = LocalDate.now();
        return repository.findByDataGarantia(hoje);
    }

    // ENVIAR NOTIFICAÇÕES
    public void enviarNotificacoesGarantia() {
        List<EpiModel> equipamentos = findEquipamentosComGarantiaExpirada();
        for (EpiModel equipamento : equipamentos) {
            String mensagem = "Equipamento: " + equipamento.getNome() + " (Patrimônio: " + equipamento.getPatrimonio() + ") expirou a data de garantia!";
            messagingTemplate.convertAndSend("/topic/notifications", mensagem); // ENVIA A NOTIFICACAO
        }
    }

}
