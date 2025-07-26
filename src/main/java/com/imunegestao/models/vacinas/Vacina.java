package com.imunegestao.models.vacinas;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vacina {
    private int id;
    private String nome;
    private String fabricante;
    private String dosesDisponiveis;
    private String dosesRecomendadas;
    private LocalDate dataValidade;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getFabricante() {return fabricante;}

    public void setFabricante(String fabricante) {this.fabricante = fabricante;}

    public String getDosesDisponiveis() {return dosesDisponiveis;}

    public void setDosesDisponiveis(String doses) {this.dosesDisponiveis = doses;}

    public String getDosesRecomendadas() {return dosesRecomendadas;}

    public void setDosesRecomendadas(String dosesRecomendadas) {this.dosesRecomendadas = dosesRecomendadas;}

    public LocalDate getValidade() {return dataValidade;}

    public void setValidade(LocalDate validade) {this.dataValidade = validade;}
}
