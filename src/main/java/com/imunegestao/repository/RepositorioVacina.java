package com.imunegestao.repository;

import com.imunegestao.models.vacinas.Vacina;

import java.util.HashMap;
import java.util.Map;

public class RepositorioVacina {
    private static RepositorioVacina instancia;
    private Map<Integer, Vacina> vacinas = new HashMap<>();
    private int proximoId = 1; // contador de ID automático

    // Construtor privado para impedir que criem novas instâncias
    private RepositorioVacina() {}



    // Método para acessar a única instância
    public static RepositorioVacina getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioVacina();
        }
        return instancia;
    }

    //metodo para que sempre que alguma vacina for excluida, a proxima vacina criada pegue o id de menor valor
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


}
