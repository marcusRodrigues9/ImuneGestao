package com.imunegestao.repository;

import com.imunegestao.models.pessoas.Cidadao;

import java.util.HashMap;
import java.util.Map;

public class RepositorioCidadao {

    private static RepositorioCidadao instancia;
    private Map<Integer, Cidadao> cidadaos = new HashMap<>();
    private int proximoId = 1; // contador de ID automático

    // Construtor privado para impedir que criem novas instâncias
    private RepositorioCidadao() {}

    // Método para acessar a única instância
    public static RepositorioCidadao getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCidadao();
        }
        return instancia;
    }

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


}
