package com.imunegestao.utils;

public class ValidacoesCidadao {

    public static void validar(String nome, String cpf, String idadeStr, String sexo, String endereco) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty())
            throw new ValidacaoException("Nome é obrigatório.");

        if (!nome.matches("^[A-Za-zÀ-ÿ\\s]+$"))
            throw new ValidacaoException("Nome deve conter apenas letras e espaços.");

        if (cpf == null || cpf.trim().isEmpty())
            throw new ValidacaoException("CPF é obrigatório.");

      //  if (!cpf.matches("\\d{11}"))
      //      throw new ValidacaoException("CPF deve conter exatamente 11 dígitos numéricos.");

        if (idadeStr == null || idadeStr.trim().isEmpty())
            throw new ValidacaoException("Idade é obrigatória.");

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
        } catch (NumberFormatException e) {
            throw new ValidacaoException("Idade inválida. Use apenas números.");
        }

        if (idade <= 0)
            throw new ValidacaoException("Idade deve ser um número positivo.");

        if (sexo == null || sexo.trim().isEmpty())
            throw new ValidacaoException("Sexo é obrigatório.");

        if (!sexo.matches("^[A-Za-zÀ-ÿ\\s]+$"))
            throw new ValidacaoException("Sexo deve conter apenas letras.");

        if (endereco == null || endereco.trim().isEmpty())
            throw new ValidacaoException("Endereço é obrigatório.");
    }
}
