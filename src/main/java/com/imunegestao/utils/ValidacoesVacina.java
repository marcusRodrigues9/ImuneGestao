package com.imunegestao.utils;

import java.time.LocalDate;

public class ValidacoesVacina {

    public static void validar(String nome, String fabricante, String dosesDisponiveisStr, String dosesRecomendadasStr, LocalDate dataValidade, String lote) throws ValidacaoException {

        if (nome == null || nome.trim().isEmpty())
            throw new ValidacaoException("Nome da vacina é obrigatório.");

        if (!nome.matches("^[A-Za-zÀ-ÿ0-9\\s]+$"))
            throw new ValidacaoException("Nome da vacina deve conter apenas letras, números e espaços.");

        if (fabricante == null || fabricante.trim().isEmpty())
            throw new ValidacaoException("Fabricante é obrigatório.");

        if (!fabricante.matches("^[A-Za-zÀ-ÿ0-9\\s]+$"))
            throw new ValidacaoException("Fabricante deve conter apenas letras, números e espaços.");

        if (dosesDisponiveisStr == null || dosesDisponiveisStr.trim().isEmpty())
            throw new ValidacaoException("Quantidade de doses disponíveis é obrigatória.");

        int dosesDisponiveis;
        try {
            dosesDisponiveis = Integer.parseInt(dosesDisponiveisStr);
        } catch (NumberFormatException e) {
            throw new ValidacaoException("Doses disponíveis deve ser um número inteiro.");
        }

        if (dosesDisponiveis < 0)
            throw new ValidacaoException("Doses disponíveis não pode ser negativo.");

        if (dosesRecomendadasStr == null || dosesRecomendadasStr.trim().isEmpty())
            throw new ValidacaoException("Quantidade de doses recomendadas é obrigatória.");

        int dosesRecomendadas;
        try {
            dosesRecomendadas = Integer.parseInt(dosesRecomendadasStr);
        } catch (NumberFormatException e) {
            throw new ValidacaoException("Doses recomendadas deve ser um número inteiro.");
        }

        if (dosesRecomendadas <= 0)
            throw new ValidacaoException("Doses recomendadas deve ser um número positivo.");

        if (dataValidade == null)
            throw new ValidacaoException("Data de validade é obrigatória.");

        if (dataValidade.isBefore(LocalDate.now()))
            throw new ValidacaoException("Data de validade não pode ser anterior à data atual.");
    }
}