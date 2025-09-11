package com.imunegestao.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.imunegestao.models.agendamento.Agendamento;
import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.utils.LocalDateAdapter;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RepositorioAgendamento {
    private static RepositorioAgendamento instancia;
    private Map<Integer, Agendamento> agendamentos = new HashMap<>();
    private int proximoId = 1;


    // --- NOVOS ATRIBUTOS PARA PERSISTÊNCIA ---
    // Define o nome do arquivo onde os dados serão salvos.
    private static final String ARQUIVO_JSON = "agendamentos.json";
    // Cria uma instância do Gson. GsonBuilder para formatar o JSON e deixá-lo legível.
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // <-- LINHA ADICIONADA
            .setPrettyPrinting()
            .create();
    // --- FIM DOS NOVOS ATRIBUTOS ---
    // Construtor privado para impedir que criem novas instâncias
    private RepositorioAgendamento() {
        carregarDados();
    }


    public static RepositorioAgendamento getInstancia() {
        if (instancia == null) instancia = new RepositorioAgendamento();
        return instancia;
    }

    public void adicionarAgendamento(Agendamento agendamento) {
        agendamento.setId(proximoId++);
        agendamentos.put(agendamento.getId(), agendamento);
        salvarDados();
    }
    public void atualizarAgendamento(Agendamento agendamento){

        if (agendamento == null || !agendamentos.containsKey(agendamento.getId())) {
            System.err.println("Erro: Tentativa de atualizar um agendamento que não existe.");
            return; // Sai do método se o agendamento for inválido.
        }

        // Atualiza o agendamento no mapa.
        agendamentos.put(agendamento.getId(), agendamento); // metodo put atualiza o valor existente se a chave ja estiver presente.
        salvarDados();
    }

    public Map<Integer, Agendamento> listarAgendamentos() {
        return agendamentos;
    }

    private int encontrarMenorIdDisponivel() {
        int id = 1;
        while (agendamentos.containsKey(id)) {
            id++;
        }
        return id;
    }


    // --- NOVOS MÉTODOS PARA SALVAR E CARREGAR ---

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(agendamentos, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados dos agendamentos: " + e.getMessage());

        }
    }

    private void carregarDados() {
        File arquivo = new File(ARQUIVO_JSON);

        // se o arquivo existe E tem conteúdo
        if (arquivo.exists() && arquivo.length() > 0) {
            try (FileReader reader = new FileReader(arquivo)) {
                Type tipoDoMapa = new TypeToken<HashMap<Integer, Agendamento>>() {}.getType();
                agendamentos = gson.fromJson(reader, tipoDoMapa);

                if (agendamentos == null) {
                    agendamentos = new HashMap<>();
                }

            } catch (IOException e) {
                System.err.println("Erro ao carregar os dados dos pacientes: " + e.getMessage());
                agendamentos = new HashMap<>();
            }
        } else {
            // Se o arquivo não existe OU está vazio, começa com um mapa novo.
            agendamentos = new HashMap<>();
        }
    }
}