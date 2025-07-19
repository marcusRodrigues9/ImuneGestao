package com.imunegestao.repository;

import com.imunegestao.models.pessoas.Cidadao;

import java.util.HashMap;
import java.util.Map;

public class RepositorioCidadao {
    private Map<Integer, Cidadao> cidadaos = new HashMap<>();
    private int proximoId = 1; // contador de ID automático

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
