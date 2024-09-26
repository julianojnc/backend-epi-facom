package br.com.facom.api.Controller;

import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.DTO.PerifericoDTO;
import br.com.facom.api.Services.PerifericoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/periferico")
public class PerifericoController {

    @Autowired
    private PerifericoService service;

    @PostMapping("/{id}/uploadFile")
    public ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file)
            throws IOException {
        if (file.isEmpty() || !"application/pdf".equals(file.getContentType())) {
            return ResponseEntity.badRequest().body("Arquivo Invalido. Somente aceito Arquivos PDF.");
        }
        String fileName = service.storeFile(id, file);
        return ResponseEntity.ok("Sucesso no Upload do Arquivo: " + fileName);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Pag<PerifericoDTO> getAllPeriferico(@RequestParam(name = "p", defaultValue = "0") @PositiveOrZero int pageNumber,
                                               @RequestParam(name = "s", defaultValue = "10") @Positive @Max(50) int pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = "nome") String sortBy,
                                               @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        return service.list(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public PerifericoDTO getPerifericoById(@PathVariable @NotNull @Positive Long id) {
        return service.findById(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public PerifericoDTO create(@Valid @RequestBody PerifericoDTO dto) {
        return service.create(dto);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/{id}")
    public PerifericoDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid PerifericoDTO dto) {
        return service.update(id, dto);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        service.delete(id);
    }
}