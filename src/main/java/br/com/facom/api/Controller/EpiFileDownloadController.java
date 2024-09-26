package br.com.facom.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Repository.EpiRepository;
import br.com.facom.api.Services.FileDownloadService;

import org.springframework.core.io.Resource;

@RestController
public class EpiFileDownloadController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @Autowired
    private EpiRepository epiRepository;

    @GetMapping("api/epi/{id}/downloadFile")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        try {
            EpiModel epi = epiRepository.findById(id)
                    .orElseThrow(() -> new Exception("Equipamento não encontrado com o ID: " + id));

            return fileDownloadService.downloadFileByEntity(epi);
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se algo der errado
        }
    }
}
