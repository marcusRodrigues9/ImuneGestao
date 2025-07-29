package com.imunegestao.models.pessoas;

import java.util.ArrayList;
import java.util.List;

public class Cidadao {
    private int id;
    private String nome;
    private String cpf;
    private int idade;
    private String sexo;
    private String endereco;
    private String numeroTelefone;
    private String email;
    private List<Integer> idsVacinasTomadas;


    public Cidadao(String nome, String cpf, int idade, String sexo, String endereco,String numeroTelefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.sexo = sexo;
        this.endereco = endereco;
        this.numeroTelefone = numeroTelefone;
        this.email = email;
        this.idsVacinasTomadas = new ArrayList<>();
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getCpf() {return cpf;}

    public void setCpf(String cpf) {this.cpf = cpf;}

    public int getIdade() {return idade;}

    public void setIdade(int idade) {this.idade = idade;}

    public String getSexo() {return sexo;}

    public void setSexo(String sexo) {this.sexo = sexo;}

    public String getEndereco() {return endereco;}

    public void setEndereco(String endereco) {this.endereco = endereco;}

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<Integer> getIdsVacinasTomadas() {
        return idsVacinasTomadas;
    }

    public void setIdsVacinasTomadas(List<Integer> idsVacinasTomadas) {
        this.idsVacinasTomadas = idsVacinasTomadas;
    }

    // Método para adicionar uma única vacina à lista
    public void adicionarVacinaTomada(int vacinaId) {
        if (!this.idsVacinasTomadas.contains(vacinaId)) { // Evita duplicatas
            this.idsVacinasTomadas.add(vacinaId);
        }
    }

    // Método para remover uma única vacina da lista
    public void removerVacinaTomada(int vacinaId) {
        this.idsVacinasTomadas.remove(Integer.valueOf(vacinaId)); // Usa Integer.valueOf para remover o objeto, não o índice
    }
}
