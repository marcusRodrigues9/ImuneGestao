package com.imunegestao.models.vacinas;

import java.time.LocalDateTime;

public class Vacina {
    private int id;
    private String nome;
    private String fabricante;
    private String doses;
    private String dosesRecomendadas;
    private LocalDateTime validade;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getFabricante() {return fabricante;}

    public void setFabricante(String fabricante) {this.fabricante = fabricante;}

    public String getDoses() {return doses;}

    public void setDoses(String doses) {this.doses = doses;}

    public String getDosesRecomendadas() {return dosesRecomendadas;}

    public void setDosesRecomendadas(String dosesRecomendadas) {this.dosesRecomendadas = dosesRecomendadas;}

    public LocalDateTime getValidade() {return validade;}

    public void setValidade(LocalDateTime validade) {this.validade = validade;}
}
