package br.com.facom.api.Controller;

import br.com.facom.api.DTO.MarcaDTO;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.Services.MarcaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/marca")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Pag<MarcaDTO> getAllMarca(@RequestParam(name = "p", defaultValue = "0") @PositiveOrZero int pageNumber,
                                     @RequestParam(name = "s", defaultValue = "10") @Positive @Max(50) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = "nome") String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        return service.list(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public MarcaDTO getMarcaById(@PathVariable @NotNull @Positive Long id) {
        return service.findById(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public MarcaDTO create(@Valid @RequestBody MarcaDTO dto) {
        return service.create(dto);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/{id}")
    public MarcaDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid MarcaDTO dto) {
        return service.update(id, dto);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        service.delete(id);
    }
}
