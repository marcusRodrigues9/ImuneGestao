package com.imunegestao.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.imunegestao.models.RegistroVacina;
import com.imunegestao.models.pessoas.Paciente;
import com.imunegestao.utils.LocalDateAdapter;

public class RepositorioPaciente {

    private static RepositorioPaciente instancia;
    private Map<Integer, Paciente> pacientes = new HashMap<>();
    private int proximoId = 1; // contador de ID automático


    // --- NOVOS ATRIBUTOS PARA PERSISTÊNCIA ---
    // Define o nome do arquivo onde os dados serão salvos.
    private static final String ARQUIVO_JSON = "pacientes.json";
    // Cria uma instância do Gson. GsonBuilder para formatar o JSON e deixá-lo legível.
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // <-- LINHA ADICIONADA
            .setPrettyPrinting()
            .create();

    // --- FIM DOS NOVOS ATRIBUTOS ---


    // Construtor privado para impedir que criem novas instâncias
    private RepositorioPaciente() {
        carregarDados();
    }


    // Método para acessar a única instância
    public static RepositorioPaciente getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioPaciente();
        }
        return instancia;
    }

    //metodo para que sempre que algum paciente for excluido, o proximo paciente criado pegue o id de menor valor
    private int encontrarMenorIdDisponivel() {
        int id = 1;
        while (pacientes.containsKey(id)) {
            id++;
        }
        return id;
    }
    public void excluirPaciente(int id) {
        pacientes.remove(id);
        salvarDados();
    }

    public void adicionarPaciente(Paciente paciente) {
        int novoId = encontrarMenorIdDisponivel();
        paciente.setId(novoId);
        pacientes.put(novoId, paciente);
        salvarDados();
    }

    public Map<Integer, Paciente> listarPacientes() {
        return pacientes;
    }


    public Paciente buscarPacientePorId(int id) {
        return pacientes.get(id);
    }
    public Paciente buscarPacientePorCpf(String cpf) {
        return pacientes.values().stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public void registrarVacinaParaPaciente(int idPaciente, RegistroVacina registro){
        Paciente paciente = buscarPacientePorId(idPaciente);
        if (paciente != null){
           paciente.adicionarVacina(registro);
           salvarDados(); // <--- Salva também após adicionar uma vacina
        }
    }


    // --- NOVOS MÉTODOS PARA SALVAR E CARREGAR ---

    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(pacientes, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados dos pacientes: " + e.getMessage());

        }
    }

    private void carregarDados() {
        File arquivo = new File(ARQUIVO_JSON);

        // se o arquivo existe E tem conteúdo
        if (arquivo.exists() && arquivo.length() > 0) {
            try (FileReader reader = new FileReader(arquivo)) {
                Type tipoDoMapa = new TypeToken<HashMap<Integer, Paciente>>() {}.getType();
                pacientes = gson.fromJson(reader, tipoDoMapa);

                if (pacientes == null) {
                    pacientes = new HashMap<>();
                }

            } catch (IOException e) {
                System.err.println("Erro ao carregar os dados dos pacientes: " + e.getMessage());
                pacientes = new HashMap<>();
            }
        } else {
            // Se o arquivo não existe OU está vazio, começa com um mapa novo.
            pacientes = new HashMap<>();
        }
}}

  

