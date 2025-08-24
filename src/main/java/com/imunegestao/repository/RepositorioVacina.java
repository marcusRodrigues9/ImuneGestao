package com.imunegestao.repository;

import com.imunegestao.models.pessoas.Paciente;
import com.imunegestao.models.vacinas.Vacina;

import java.util.HashMap;
import java.util.Map;

public class RepositorioVacina {
    private static RepositorioVacina instancia;
    private Map<Integer, Vacina> vacinas = new HashMap<>();
    private int proximoId = 1;

    private RepositorioVacina() {}

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
    }

    public Map<Integer, Vacina> listarVacinas() {
        return vacinas;
    }

    public Vacina buscarVacinaPorId(int id) {
        return vacinas.get(id);
    }
}
