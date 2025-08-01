package com.imunegestao.models.enums;

public enum StatusAgendamento {
    AGENDADO("Agendado"),
    CONFIRMADO("Confirmado"),
    REALIZADO("Realizado"),
    CANCELADO("Cancelado");
    
    private final String descricao;
    StatusAgendado(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
