package com.imunegestao.models.pessoas;

import com.imunegestao.models.RegistroVacina;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private String numeroSus;
  /*  private int idade;*/
    private LocalDate dataNascimento;
    private String sexo;
    private String endereco;
    private String numeroTelefone;
    private String email;

    private List<RegistroVacina> vacinasTomadas = new ArrayList<>();


    public Paciente(String nome, String cpf,String numeroSus, LocalDate dataNascimento, String sexo, String endereco, String email,String numeroTelefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.numeroSus = numeroSus;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.endereco = endereco;
        this.numeroTelefone = numeroTelefone;
        this.email = email;
    }

    public Paciente(List<RegistroVacina> vacinasTomadas, String email, String numeroTelefone, String endereco, String sexo, String numeroSus, LocalDate dataNascimento, String cpf, String nome, int id) {
        this.vacinasTomadas = vacinasTomadas;
        this.email = email;
        this.numeroTelefone = numeroTelefone;
        this.endereco = endereco;
        this.sexo = sexo;
        this.numeroSus = numeroSus;
      /*  this.idade = idade; */
        this.dataNascimento = dataNascimento;
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
    public void removerVacina(RegistroVacina registro){this.vacinasTomadas.remove(registro);}


    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getCpf() {return cpf;}

    public void setCpf(String cpf) {this.cpf = cpf;}

    public int getIdade() {
        if (dataNascimento == null) return 0;
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

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

    public String getNumeroSus() {
        return numeroSus;
    }

    public void setNumeroSus(String numeroSus) {
        this.numeroSus = numeroSus;
    }
}
