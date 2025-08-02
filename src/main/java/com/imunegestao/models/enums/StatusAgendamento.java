package com.imunegestao.models.enums;

public enum StatusAgendamento {
    AGENDADO,
    CONFIRMADO,
    REALIZADO,
    CANCELADO;

    public int getPrioridade() {
        switch(this) {
            case AGENDADO: return 0;      // maior prioridade
            case CONFIRMADO: return 1;
            case REALIZADO: return 2;
            case CANCELADO: return 3;     // menor prioridade
            default: return 99;
        }
    }
    @Override
    public String toString() {
        switch (this) {
            case CANCELADO: return "Cancelado";
            case AGENDADO: return "Agendado";
            case REALIZADO: return "Realizado";
            case CONFIRMADO: return "Confirmado";
            default: return super.toString();
        }
    }
}