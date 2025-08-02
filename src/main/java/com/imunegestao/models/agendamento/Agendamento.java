package com.imunegestao.models.agendamento;

import com.imunegestao.models.enums.StatusAgendamento;
import com.imunegestao.models.pessoas.Cidadao;
import com.imunegestao.models.vacinas.Vacina;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private int id;
    private Cidadao cidadao;
    private LocalDate data;
    private Vacina vacina;
    private int doses;
    private String hora;
    private StatusAgendamento status; // agendado, confirmado, realizado, cancelado

    public Agendamento( Cidadao cidadao, LocalDate data, Vacina vacina,int doses, String hora, StatusAgendamento status) {
        this.cidadao = cidadao;
        this.data = data;
        this.vacina = vacina;
        this.doses = doses;
        this.hora = hora;
        this.status = status;
    }
    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getCpf() {
        return cidadao != null ? cidadao.getCpf() : "";
    }

    public String getNome() {
        return cidadao != null ? cidadao.getNome() : "";
    }
    public String getVacina() {
        return vacina != null ? vacina.getNome() : "";
    }


    public LocalDate getData() {return data;}

    public void setData(LocalDate data) {this.data = data;}

    public String getHora() {return hora;}

    public void setHora(String hora) {this.hora = hora;}

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}