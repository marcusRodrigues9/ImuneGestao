package com.imunegestao.utils;

import java.time.LocalDate;
import java.time.Period;

public class ValidacoesPaciente {

    public static void validar(String nome, String cpf, LocalDate dataNascimento, String sexo,
                               String endereco, String email, String telefone) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty())
            throw new ValidacaoException("Nome é obrigatório.");

        if (!nome.matches("^[A-Za-zÀ-ÿ\\s]+$"))
            throw new ValidacaoException("Nome deve conter apenas letras e espaços.");

        if (cpf == null || cpf.trim().isEmpty())
            throw new ValidacaoException("CPF é obrigatório.");

        // if (!cpf.matches("\\d{11}"))
        //     throw new ValidacaoException("CPF deve conter exatamente 11 dígitos numéricos.");

        if (dataNascimento == null)
            throw new ValidacaoException("Data de nascimento é obrigatória.");

        // Calcula a idade
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade <= 0)
            throw new ValidacaoException("Data de nascimento inválida.");

        if (sexo == null || sexo.trim().isEmpty())
            throw new ValidacaoException("Sexo é obrigatório.");

        if (!sexo.matches("^[A-Za-zÀ-ÿ\\s]+$"))
            throw new ValidacaoException("Sexo deve conter apenas letras.");

        if (endereco == null || endereco.trim().isEmpty())
            throw new ValidacaoException("Endereço é obrigatório.");

        if (email == null || email.trim().isEmpty()) {
            throw new ValidacaoException("E-mail é obrigatório.");
        }
        if (!email.matches("^[\\w.-]+@[\\w-]+\\.[a-zA-Z]{2,6}$")) {
            throw new ValidacaoException("E-mail inválido. Digite no formato: exemplo@dominio.com");
        }

        if (telefone == null || telefone.trim().isEmpty()) {
            throw new ValidacaoException("Telefone é obrigatório.");
        }
        if (!telefone.matches("^\\d{10,11}$")) {
            throw new ValidacaoException("Telefone inválido. Digite apenas números com DDD (10 ou 11 dígitos). Ex: 11987654321");
        }
    }
}
