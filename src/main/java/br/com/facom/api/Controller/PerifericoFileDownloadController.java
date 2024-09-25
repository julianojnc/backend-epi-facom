package br.com.facom.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.facom.api.Model.PerifericoModel;
import br.com.facom.api.Repository.PerifericoRepository;
import br.com.facom.api.Services.FileDownloadService;

import org.springframework.core.io.Resource;

@RestController
public class PerifericoFileDownloadController {
    @Autowired
    private FileDownloadService fileDownloadService;

    @Autowired
    private PerifericoRepository perifericoRepository;

    @GetMapping("api/periferico/{id}/downloadFile")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        try {
            PerifericoModel periferico = perifericoRepository.findById(id)
                    .orElseThrow(() -> new Exception("Periferico não encontrado com o ID: " + id));

            return fileDownloadService.downloadFileByEntity(periferico);
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se algo der errado
        }
    }
}
