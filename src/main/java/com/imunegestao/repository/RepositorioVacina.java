package com.imunegestao.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.imunegestao.models.pessoas.Paciente;
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


public class RepositorioVacina {
    private static RepositorioVacina instancia;
    private Map<Integer, Vacina> vacinas = new HashMap<>();
    private int proximoId = 1;


    // --- NOVOS ATRIBUTOS PARA PERSISTÊNCIA ---
    // Define o nome do arquivo onde os dados serão salvos.
    private static final String ARQUIVO_JSON = "vacinas.json";
    // Cria uma instância do Gson. GsonBuilder para formatar o JSON e deixá-lo legível.
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // <-- LINHA ADICIONADA
            .setPrettyPrinting()
            .create();
    // --- FIM DOS NOVOS ATRIBUTOS ---
    // Construtor privado para impedir que criem novas instâncias
    private RepositorioVacina() {
        carregarDados();
    }

    // Método para acessar a única instância
    public static RepositorioVacina getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioVacina();
        }
        return instancia;
    }

    private int encontrarMenorIdDisponivel() {
        int id = 1;
        while (vacinas.containsKey(id)) {
            id++;
        }
        return id;
    }

    public void adicionarVacina(Vacina vacina) {
        int novoId = encontrarMenorIdDisponivel();
        vacina.setId(novoId);
        vacinas.put(novoId, vacina);
        salvarDados();
    }


    public Map<Integer, Vacina> listarVacinas() {
        return vacinas;
    }

    public Vacina buscarVacinaPorId(int id) {
        return vacinas.get(id);
    }

    public void gastarDoseVacina(int quatidadeDose, Vacina vacina){
        int quantidadeRestante = vacina.getDosesDisponiveis() - quatidadeDose;
        vacina.setDosesDisponiveis(quantidadeRestante);
        salvarDados();
    }
    public void adicionarDoseVacina(int quatidadeDose, Vacina vacina){
        int quantidadeRestante = vacina.getDosesDisponiveis() + quatidadeDose;
        vacina.setDosesDisponiveis(quantidadeRestante);
        salvarDados();
    }

    // --- NOVOS MÉTODOS PARA SALVAR E CARREGAR ---

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(vacinas, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados dos pacientes: " + e.getMessage());

        }
    }

    private void carregarDados() {
        File arquivo = new File(ARQUIVO_JSON);

        // se o arquivo existe E tem conteúdo
        if (arquivo.exists() && arquivo.length() > 0) {
            try (FileReader reader = new FileReader(arquivo)) {
                Type tipoDoMapa = new TypeToken<HashMap<Integer, Vacina>>() {}.getType();
                vacinas = gson.fromJson(reader, tipoDoMapa);

                if (vacinas == null) {
                    vacinas = new HashMap<>();
                }

            } catch (IOException e) {
                System.err.println("Erro ao carregar os dados dos pacientes: " + e.getMessage());
                vacinas = new HashMap<>();
            }
        } else {
            // Se o arquivo não existe OU está vazio, começa com um mapa novo.
            vacinas = new HashMap<>();
        }
    }
}
