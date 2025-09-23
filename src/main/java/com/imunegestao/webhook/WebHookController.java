package com.imunegestao.webhook;

import com.google.gson.Gson;
import com.imunegestao.models.agendamento.Agendamento;
import com.imunegestao.models.enums.StatusAgendamento;
import com.imunegestao.repository.RepositorioAgendamento;

import static spark.Spark.*;

public class WebHookController {
    private static final Gson gson = new Gson();
    private static final RepositorioAgendamento repo = RepositorioAgendamento.getInstancia();

    public static void main(String[] args) {
        port(8080);

        post("/webhook", (req, res) -> {
            res.type("application/json");
            System.out.println("Recebido payload: " + req.body());

            try {
                WebhookPayload payload = gson.fromJson(req.body(), WebhookPayload.class);

                // Correção: Buscar o agendamento diretamente no Map com .get()
                Agendamento ag = repo.listarAgendamentos().get(payload.getId());

                if (ag == null) {
                    res.status(404);
                    return gson.toJson(new Resposta("Agendamento não encontrado com o ID: " + payload.getId()));
                }

                ag.setStatus(payload.getStatus());
                repo.atualizarAgendamento(ag); // Este método precisa salvar o mapa atualizado no arquivo JSON

                return gson.toJson(new Resposta("Agendamento " + ag.getId() + " atualizado para " + ag.getStatus()));
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return gson.toJson(new Resposta("Erro interno: " + e.getMessage()));
            }
        });
    }

    public static class WebhookPayload {
        private int id;
        private StatusAgendamento status;

        public WebhookPayload() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public StatusAgendamento getStatus() {
            return status;
        }

        public void setStatus(StatusAgendamento status) {
            this.status = status;
        }
    }

    static class Resposta {
        String mensagem;
        Resposta(String msg) { this.mensagem = msg; }
    }
}