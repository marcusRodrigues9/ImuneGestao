package com.imunegestao.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MailSender {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper;

    public MailSender(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void send(String email, String nome, String data, String hora) {
        try {
            String body = mapper.writeValueAsString(new Request(email, nome, data, hora));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9090/send-email"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException("Falhou ao chamar POST /send-email", e);
        }
    }

}