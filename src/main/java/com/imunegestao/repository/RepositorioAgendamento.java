package com.imunegestao.repository;

import com.imunegestao.models.agendamento.Agendamento;

import java.util.HashMap;
import java.util.Map;

public class RepositorioAgendamento {
    private static RepositorioAgendamento instancia;
    private Map<Integer, Agendamento> agendamentos = new HashMap<>();
    private int proximoId = 1;

    private RepositorioAgendamento() {}

    public static RepositorioAgendamento getInstancia() {
        if (instancia == null) instancia = new RepositorioAgendamento();
        return instancia;
    }

    public void adicionarAgendamento(Agendamento agendamento) {
        int id = encontrarMenorIdDisponivel();
        agendamento.setId(id);
        agendamentos.put(id, agendamento);
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
}