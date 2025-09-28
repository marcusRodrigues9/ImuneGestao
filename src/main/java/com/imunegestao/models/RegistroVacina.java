package com.imunegestao.models;

import com.imunegestao.models.pessoas.ProfissionalSaude;
import com.imunegestao.models.vacinas.Vacina;

import java.time.LocalDate;

public class RegistroVacina {
    private LocalDate dataAplicacao;
    private Vacina vacina;

    public RegistroVacina(LocalDate dataAplicacao, Vacina vacina) {
        this.dataAplicacao = dataAplicacao;
        this.vacina = vacina;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }


    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }


}
