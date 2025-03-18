package br.com.facom.api.Controller;

import br.com.facom.api.Model.TokenModel;
import br.com.facom.api.Repository.DeviceTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DeviceTokenController {

    @Autowired
    private DeviceTokenRepository repository;

    // Logger correto para a classe atual
    private static final Logger logger = LoggerFactory.getLogger(DeviceTokenController.class);

    @PostMapping("/salvar-token")
    public ResponseEntity<String> salvarToken(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token");
            logger.info("Recebendo token: {}", token);

            // Validação do token
            if (token == null || token.isEmpty()) {
                logger.error("Token vazio recebido");
                return ResponseEntity.badRequest().body("Token inválido");
            }

            // Verifica se o token já existe
            Optional<TokenModel> tokenExistente = Optional.ofNullable(repository.findByToken(token));
            if (tokenExistente.isPresent()) {
                logger.warn("Token já existe: {}", token);
                return ResponseEntity.ok("Token já registrado");
            }

            // Salva o novo token
            TokenModel tokenModel = new TokenModel();
            tokenModel.setToken(token);
            repository.save(tokenModel);

            logger.info("Token salvo com sucesso: {}", token);
            return ResponseEntity.ok("Token salvo com sucesso");

        } catch (Exception e) {
            logger.error("Erro ao salvar token: {}", e.getMessage(), e); // Log com stack trace
            return ResponseEntity.status(500).body("Erro interno ao processar o token");
        }
    }
}