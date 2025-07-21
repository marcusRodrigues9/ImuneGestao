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

    public void adicionarCidadao(Cidadao cidadao) {
        cidadao.setId(proximoId); // define o ID automaticamente
        cidadaos.put(proximoId, cidadao);
        proximoId++;
    }

    public Map<Integer, Cidadao> listarCidadaos() {
        return cidadaos;
    }

    public Cidadao buscarPorId(int id) {
        return cidadaos.get(id);
    }
}
