package com.imunegestao.models.agendamento;

import com.imunegestao.models.enums.Situacao;

import java.time.LocalDateTime;

public class Agendamento {
    private int id;
    private Cidadao cidadao;
    private LocalDateTime dataAgendada;
    private Situacao situacao;
}
