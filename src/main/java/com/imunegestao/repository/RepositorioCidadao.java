package com.imunegestao.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.imunegestao.models.RegistroVacina;
import com.imunegestao.models.pessoas.Cidadao;

public class RepositorioCidadao {

    private static RepositorioCidadao instancia;
    private Map<Integer, Cidadao> cidadaos = new HashMap<>();
    private int proximoId = 1; // contador de ID automático

    // --- NOVOS ATRIBUTOS PARA PERSISTÊNCIA ---
    // Define o nome do arquivo onde os dados serão salvos.
    private static final String ARQUIVO_JSON = "cidadaos.json";
    // Cria uma instância do Gson. Usamos o GsonBuilder para formatar o JSON e deixá-lo legível.
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // --- FIM DOS NOVOS ATRIBUTOS ---


    // Construtor privado para impedir que criem novas instâncias
    private RepositorioCidadao() {
    }


    // Método para acessar a única instância
    public static RepositorioCidadao getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCidadao();
        }
        return instancia;
    }

    //metodo para que sempre que algum cidadao for excluido, o proximo cidadao criado pegue o id de menor valor
    private int encontrarMenorIdDisponivel() {
        int id = 1;
        while (cidadaos.containsKey(id)) {
            id++;
        }
        return id;
    }

    public void adicionarCidadao(Cidadao cidadao) {
        int novoId = encontrarMenorIdDisponivel();
        cidadao.setId(novoId);
        cidadaos.put(novoId, cidadao);
    }

    public Map<Integer, Cidadao> listarCidadaos() {
        return cidadaos;
    }


    public Cidadao buscarCidadaoPorId(int id) {
        return cidadaos.get(id);
    }
    public Cidadao buscarCidadaoPorCpf(String cpf) {
        return cidadaos.values().stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public void registrarVacinaParaCidadao(int idCidadao, RegistroVacina registro){
        Cidadao cidadao = buscarCidadaoPorId(idCidadao);
        if (cidadao != null){
           cidadao.adicionarVacina(registro);
        }
    }
    // --- NOVOS MÉTODOS PARA SALVAR E CARREGAR ---

    /**
     * Salva o mapa de cidadãos atual para o arquivo cidadaos.json.
     */
    private void salvarDados() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            gson.toJson(cidadaos, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados dos cidadãos: " + e.getMessage());
            // Aqui você poderia mostrar um alerta de erro para o usuário
        }
    }

    /**
     * Carrega os dados do arquivo cidadaos.json. Se o arquivo não existir,
     * inicializa um mapa vazio.
     */
    private void carregarDados() {
        File arquivo = new File(ARQUIVO_JSON);
        if (arquivo.exists()) {
            try (FileReader reader = new FileReader(arquivo)) {
                // Precisamos dizer ao Gson o tipo exato dos dados: um Mapa de Integer para Cidadao
                Type tipoDoMapa = new TypeToken<HashMap<Integer, Cidadao>>() {}.getType();
                cidadaos = gson.fromJson(reader, tipoDoMapa);

                // Caso o arquivo esteja vazio ou mal formatado
                if (cidadaos == null) {
                    cidadaos = new HashMap<>();
                }

            } catch (IOException e) {
                System.err.println("Erro ao carregar os dados dos cidadãos: " + e.getMessage());
                cidadaos = new HashMap<>(); // Em caso de erro, começa com uma lista vazia
            }
        } else {
            // Se o arquivo não existe, apenas inicializa a lista como vazia.
            cidadaos = new HashMap<>();
        }
    }
}

  

