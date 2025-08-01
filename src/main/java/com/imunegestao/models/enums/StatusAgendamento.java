package com.imunegestao.models.enums;

public enum StatusAgendamento {
    CANCELADO,
    AGENDADO,
    REALIZADO,
    CONFIRMADO;

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