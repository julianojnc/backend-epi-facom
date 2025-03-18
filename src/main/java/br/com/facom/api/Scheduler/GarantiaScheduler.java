package br.com.facom.api.Scheduler;

import br.com.facom.api.Model.EpiModel;
import br.com.facom.api.Model.PerifericoModel;
import br.com.facom.api.Repository.EpiRepository;
import br.com.facom.api.Repository.PerifericoRepository;
import br.com.facom.api.Services.ExpoPushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@Component
public class GarantiaScheduler {

    private static final Logger logger = LoggerFactory.getLogger(GarantiaScheduler.class);

    @Autowired
    private EpiRepository epiRepository;

    @Autowired
    private PerifericoRepository perifericoRepository;

    @Autowired
    private ExpoPushNotificationService expoPushNotificationService;

    @Scheduled(cron = "0 0 0 */1 * ?") // Executa todo dia "0 0 0 */1 * ?" // teste 10 sec "*/10 * * * * ?" // por minuto "0 */1 * * * ?"
    public void verificarGarantia() {
        LocalDate hoje = LocalDate.now();
        LocalDate umaSemanaAntes = hoje.plusWeeks(1);
        LocalDate umMesAntes = hoje.plusMonths(1);

        // Processamento para EPIs
        processarEpis(hoje, umaSemanaAntes, umMesAntes);

        // Processamento para Periféricos
        processarPerifericos(hoje, umaSemanaAntes, umMesAntes);
    }

    private void processarEpis(LocalDate hoje, LocalDate umaSemanaAntes, LocalDate umMesAntes) {
        List<EpiModel> episHoje = epiRepository.findByDataGarantia(hoje);
        List<EpiModel> episUmaSemana = epiRepository.findByDataGarantia(umaSemanaAntes);
        List<EpiModel> episUmMes = epiRepository.findByDataGarantia(umMesAntes);

        enviarNotificacoesEpis(episHoje, "hoje");
        enviarNotificacoesEpis(episUmaSemana, "em uma semana");
        enviarNotificacoesEpis(episUmMes, "em um mês");
    }

    private void processarPerifericos(LocalDate hoje, LocalDate umaSemanaAntes, LocalDate umMesAntes) {
        List<PerifericoModel> perifsHoje = perifericoRepository.findByDataGarantia(hoje);
        List<PerifericoModel> perifsUmaSemana = perifericoRepository.findByDataGarantia(umaSemanaAntes);
        List<PerifericoModel> perifsUmMes = perifericoRepository.findByDataGarantia(umMesAntes);

        enviarNotificacoesPerifericos(perifsHoje, "hoje");
        enviarNotificacoesPerifericos(perifsUmaSemana, "em uma semana");
        enviarNotificacoesPerifericos(perifsUmMes, "em um mês");
    }

    private void enviarNotificacoesEpis(List<EpiModel> equipamentos, String prazo) {
        for (EpiModel equipamento : equipamentos) {
            String mensagem = String.format(
                    "A Garantia do Equipamento: %s (Patrimônio: %s) expira %s dia %s",
                    equipamento.getNome(),
                    equipamento.getPatrimonio(),
                    prazo,
                    equipamento.getDataGarantia()
            );

            logger.info("Enviando notificação para EPI: {}", equipamento.getNome());
            expoPushNotificationService.enviarNotificacao("⚠️ Alerta Garantia", mensagem);
        }
    }

    private void enviarNotificacoesPerifericos(List<PerifericoModel> perifericos, String prazo) {
        for (PerifericoModel periferico : perifericos) {
            String mensagem = String.format(
                    "A Garantia do Periférico: %s (Patrimônio: %s) expira %s dia %s",
                    periferico.getNome(),
                    periferico.getPatrimonio(),
                    prazo,
                    periferico.getDataGarantia()
            );

            logger.info("Enviando notificação para Periférico: {}", periferico.getNome());
            expoPushNotificationService.enviarNotificacao("⚠️ Alerta Garantia", mensagem);
        }
    }
}
