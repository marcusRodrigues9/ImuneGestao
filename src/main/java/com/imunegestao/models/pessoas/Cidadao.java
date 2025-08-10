package com.imunegestao.models.pessoas;

import com.imunegestao.models.RegistroVacina;

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

    private List<RegistroVacina> vacinasTomadas = new ArrayList<>();


    public Cidadao(String nome, String cpf, int idade, String sexo, String endereco,String numeroTelefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.sexo = sexo;
        this.endereco = endereco;
        this.numeroTelefone = numeroTelefone;
        this.email = email;
    }

    public Cidadao(List<RegistroVacina> vacinasTomadas, String email, String numeroTelefone, String endereco, String sexo, int idade, String cpf, String nome, int id) {
        this.vacinasTomadas = vacinasTomadas;
        this.email = email;
        this.numeroTelefone = numeroTelefone;
        this.endereco = endereco;
        this.sexo = sexo;
        this.idade = idade;
        this.cpf = cpf;
        this.nome = nome;
        this.id = id;
    }

    public List<RegistroVacina> getVacinasTomadas() {
        return vacinasTomadas;
    }
    public void adicionarVacina(RegistroVacina registro) {
        this.vacinasTomadas.add(registro);
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




}
