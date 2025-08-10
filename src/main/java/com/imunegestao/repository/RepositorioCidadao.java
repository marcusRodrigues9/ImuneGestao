package com.imunegestao.repository;

import java.util.HashMap;
import java.util.Map;

import com.imunegestao.models.RegistroVacina;
import com.imunegestao.models.pessoas.Cidadao;

public class RepositorioCidadao {

    private static RepositorioCidadao instancia;
    private Map<Integer, Cidadao> cidadaos = new HashMap<>();
    private int proximoId = 1; // contador de ID automático

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
}

  

