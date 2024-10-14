package br.com.facom.api.Controller;

import br.com.facom.api.DTO.EpiUsuarioDTO;
import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.Services.EpiUsuarioService;
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
@RequestMapping("/api/epi-usuario")
public class EpiUsuarioController {

    @Autowired
    private EpiUsuarioService service;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Pag<EpiUsuarioDTO> getAllEpiUsuario(@RequestParam(name = "p", defaultValue = "0") @PositiveOrZero int pageNumber,
                                               @RequestParam(name = "s", defaultValue = "10") @Positive @Max(50) int pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                               @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        return service.list(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public EpiUsuarioDTO getEpiUsuarioById(@PathVariable @NotNull @Positive Long id) {
        return service.findById(id);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public EpiUsuarioDTO create(@Valid @RequestBody EpiUsuarioDTO dto) {
        return service.create(dto);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/{id}")
    public EpiUsuarioDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid EpiUsuarioDTO dto) {
        return service.update(id, dto);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        service.delete(id);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PatchMapping("/{id}/desvincula")
    public void desvincula(@PathVariable Long id){ service.desvincula(id);}

}
