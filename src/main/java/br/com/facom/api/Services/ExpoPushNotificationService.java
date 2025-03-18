package br.com.facom.api.Services;

import br.com.facom.api.Model.TokenModel;
import br.com.facom.api.Repository.DeviceTokenRepository;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpoPushNotificationService {

    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";
    private final OkHttpClient client = new OkHttpClient();

    @Autowired
    private DeviceTokenRepository deviceTokenRepository;

    // Envia notificações para todos os tokens registrados
    public void enviarNotificacao(String titulo, String mensagem) {
        List<String> tokens = deviceTokenRepository.findAll()
                .stream()
                .map(TokenModel::getToken)
                .collect(Collectors.toList());

        for (String token : tokens) {
            String json = String.format(
                    "{\"to\": \"%s\", \"title\": \"%s\", \"body\": \"%s\"}",
                    token, titulo, mensagem
            );

            RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url(EXPO_PUSH_URL)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Erro ao enviar notificação: " + response.body().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}