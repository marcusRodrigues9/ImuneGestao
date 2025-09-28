package com.imunegestao.models.vacinas;

import com.sun.source.doctree.BlockTagTree;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vacina {
    private int id;
    private String nome;
    private String fabricante;
    private int dosesDisponiveis;
    private int dosesRecomendadas;
    private LocalDate dataValidade;
    private String lote;


    public Vacina(String nome, String fabricante, int dosesDisponiveis, int dosesRecomendadas, LocalDate dataValidade, String lote) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.dosesDisponiveis = dosesDisponiveis;
        this.dosesRecomendadas = dosesRecomendadas;
        this.dataValidade = dataValidade;
        this.lote = lote;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getFabricante() {return fabricante;}

    public void setFabricante(String fabricante) {this.fabricante = fabricante;}

    public int getDosesDisponiveis() {return dosesDisponiveis;}

    public void setDosesDisponiveis(int doses) {this.dosesDisponiveis = doses;}

    public int getDosesRecomendadas() {return dosesRecomendadas;}

    public void setDosesRecomendadas(int dosesRecomendadas) {this.dosesRecomendadas = dosesRecomendadas;}

    public LocalDate getDataValidade() {return dataValidade;}

    public void setDataValidade(LocalDate validade) {this.dataValidade = validade;}
}
