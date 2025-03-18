package br.com.facom.api.Controller;

import br.com.facom.api.DTO.ManutencaoDTO;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.Services.ManutencaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/manutencao")
public class ManutencaoController {

    @Autowired
    private ManutencaoService service;

    @PostMapping("/{id}/uploadFile")
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file)
            throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo Invalido. O arquivo está vazio.");
        }

        // Lista de tipos MIME permitidos
        String contentType = file.getContentType();
        List<String> allowedTypes = Arrays.asList("application/pdf", "image/jpeg", "image/png");

        if (!allowedTypes.contains(contentType)) {
            return ResponseEntity.badRequest().body("Tipo de arquivo não suportado.");
        }

        try {
            String fileName = service.storeFile(id, file);
            return ResponseEntity.ok("Sucesso no Upload do Arquivo: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o arquivo.");
        }
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Pag<ManutencaoDTO> getAllManutencao(
            @RequestParam(name = "p", defaultValue = "0") @PositiveOrZero int pageNumber,
            @RequestParam(name = "s", defaultValue = "10") @Positive @Max(50) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dataIniManutencao") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        return service.list(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ManutencaoDTO getManutencaoById(@PathVariable @NotNull @Positive Long id) {
        return service.findById(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public ManutencaoDTO create(@Valid @RequestBody ManutencaoDTO dto) {
        return service.create(dto);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/{id}")
    public ManutencaoDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid ManutencaoDTO dto) {
        return service.update(id, dto);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        service.delete(id);
    }
}