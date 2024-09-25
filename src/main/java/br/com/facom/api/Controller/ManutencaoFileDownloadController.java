package br.com.facom.api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.facom.api.Model.ManutencaoModel;
import br.com.facom.api.Repository.ManutencaoRepository;
import br.com.facom.api.Services.FileDownloadService;

import org.springframework.core.io.Resource;

@RestController
public class ManutencaoFileDownloadController {
    @Autowired
    private FileDownloadService fileDownloadService;

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @GetMapping("api/manutencao/{id}/downloadFile")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        try {
            ManutencaoModel manutencao = manutencaoRepository.findById(id)
                    .orElseThrow(() -> new Exception("Manutencao não encontrado com o ID: " + id));

            return fileDownloadService.downloadFileByEntity(manutencao);
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna 404 se algo der errado
        }
    }
}
