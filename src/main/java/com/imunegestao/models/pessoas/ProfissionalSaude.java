package com.imunegestao.models.pessoas;

public class ProfissionalSaude {
    private int id;
    private String nome;
    private String senha;
    private String registroAcesso;

    public ProfissionalSaude(int id, String nome, String senha) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }
}
