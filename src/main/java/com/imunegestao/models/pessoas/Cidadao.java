package com.imunegestao.models.pessoas;

public class Cidadao {
    private int id;
    private String nome;
    private String cpf;
    private int idade;
    private String sexo;
    private String endereco;
    private String bairro;

    public Cidadao(int id, String nome, String cpf, int idade, String sexo, String endereco, String bairro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.sexo = sexo;
        this.endereco = endereco;
        this.bairro = bairro;
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

    public String getBairro() {return bairro;}

    public void setBairro(String bairro) {this.bairro = bairro;}
}
