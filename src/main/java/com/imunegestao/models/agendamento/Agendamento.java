package com.imunegestao.models.agendamento;

import com.imunegestao.models.enums.StatusAgendamento;
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
    private StatusAgendamento status; // agendado, confirmado, realizado, cancelado

    public Agendamento(String nome, Cidadao cidadao, Vacina vacina, LocalDate data, String hora, StatusAgendamento status) {
        this.nome = nome;
        this.cidadao = cidadao;
        this.vacina = vacina;
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public Cidadao getCidadao() {return cidadao;}

    public void setCidadao(Cidadao cidadao) {this.cidadao = cidadao;}

    public Vacina getVacina() {return vacina;}

    public void setVacina(Vacina vacina) {this.vacina = vacina;}

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