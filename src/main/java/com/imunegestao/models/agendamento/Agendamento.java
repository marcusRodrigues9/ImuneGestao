package com.imunegestao.models.agendamento;

import com.imunegestao.models.pessoas.Cidadao;
import com.imunegestao.models.vacinas.Vacina;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private int id;
    private String nome;
    private Cidadao cidadao;
    private Vacina vacina;
    private LocalDate data;
    private String hora;
    private String status; // agendado, confirmado, realizado, cancelado

    public Agendamento(String nome, Cidadao cidadao, Vacina vacina, LocalDate data, String hora, String status) {
        this.nome = nome;
        this.cidadao = cidadao;
        this.vacina = vacina;
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

}