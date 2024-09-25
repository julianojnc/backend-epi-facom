package br.com.facom.api.Services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Model.ManutencaoModel;
import br.com.facom.api.Model.PerifericoModel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileDownloadService {

    // Método genérico que funciona para qualquer entidade com caminho de arquivo
    public ResponseEntity<Resource> downloadFileByFilePath(String filePath) throws Exception {
        Path path = Paths.get(filePath).normalize();

        // Verifica se o arquivo existe no caminho
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) {
            throw new Exception("Arquivo não encontrado no caminho: " + filePath);
        }

        // Determina o tipo de conteúdo do arquivo
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream"; // Default para arquivos binários
        }

        // Retorna o arquivo com os cabeçalhos corretos para download
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // Método específico que busca o caminho do arquivo pelo ID da entidade
    public ResponseEntity<Resource> downloadFileByEntity(Object entity) throws Exception {
        if (entity instanceof EpiModel) {
            EpiModel epi = (EpiModel) entity;
            return downloadFileByFilePath(epi.getFilePath());
        } else if (entity instanceof ManutencaoModel) {
            ManutencaoModel manutencao = (ManutencaoModel) entity;
            return downloadFileByFilePath(manutencao.getFilePath());
        } else if (entity instanceof PerifericoModel) {
            PerifericoModel periferico = (PerifericoModel) entity;
            return downloadFileByFilePath(periferico.getFilePath());
        } else {
            throw new Exception("Entidade desconhecida");
        }
    }
}
