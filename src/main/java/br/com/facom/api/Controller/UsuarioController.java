package br.com.facom.api.Controller;

import br.com.facom.api.DTO.Paginacao.Pag;
import br.com.facom.api.DTO.UsuarioDTO;
import br.com.facom.api.services.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public Pag<UsuarioDTO> listUsuarios(@RequestParam(name = "p",defaultValue = "0") @PositiveOrZero int pageNumber,
                                        @RequestParam(name ="s",defaultValue = "10") @Positive @Max(50) int pageSize,
                                        @RequestParam(value = "sortBy", defaultValue = "nome") String sortBy,
                                        @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        return usuarioService.list(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public UsuarioDTO getUsuariobyId(@PathVariable Long id) { return usuarioService.findById(id); }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public UsuarioDTO create(@RequestBody @NotNull @Valid UsuarioDTO usuarioDTO) { return usuarioService.create(usuarioDTO); }

    @PutMapping("/{id}")
    public UsuarioDTO upate(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid UsuarioDTO usuarioDTO){
        return usuarioService.update(id,usuarioDTO);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NotNull @Positive Long id){
        usuarioService.delete(id);
    }

}
