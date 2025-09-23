package com.imunegestao.webhook;
import static spark.Spark.*;

public class WhatsAppWebhook {
    public static void startWebhook() {
        port(8080); // O servidor vai rodar em http://localhost:8080

        // Rota para receber mensagens do WhatsApp
        post("/webhook", (req, res) -> {
            String body = req.body();
            System.out.println("Mensagem recebida do WhatsApp: " + body);

            // Aqui você processa o JSON que o WhatsApp manda
            // Exemplo: se paciente respondeu "1", marcar como confirmado no JSON

            res.status(200);
            return "EVENT_RECEIVED";
        });

        // Rota para verificação inicial do webhook (Meta exige isso)
        get("/webhook", (req, res) -> {
            String mode = req.queryParams("hub.mode");
            String token = req.queryParams("hub.verify_token");
            String challenge = req.queryParams("hub.challenge");

            if ("subscribe".equals(mode) && "SEU_TOKEN".equals(token)) {
                return challenge;
            } else {
                res.status(403);
                return "Erro de verificação";
            }
        });
    }
}
