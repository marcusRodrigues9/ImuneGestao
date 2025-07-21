package com.imunegestao.utils;

public class ValidacoesCidadao {

    public static String validar(String nome, String cpf, String idadeStr, String sexo, String endereco) {
        if (nome == null || nome.trim().isEmpty())
            return "Nome é obrigatório.";
        if (cpf == null || cpf.trim().isEmpty())
            return "CPF é obrigatório.";
        if (!cpf.matches("\\d{11}"))
            return "CPF deve conter exatamente 11 dígitos numéricos.";
        if (idadeStr == null || idadeStr.trim().isEmpty())
            return "Idade é obrigatória.";
        try {
            int idade = Integer.parseInt(idadeStr);
            if (idade <= 0)
                return "Idade deve ser um número positivo.";
        } catch (NumberFormatException e) {
            return "Idade inválida. Use apenas números.";
        }
        if (sexo == null || sexo.trim().isEmpty())
            return "Sexo é obrigatório.";
        if (endereco == null || endereco.trim().isEmpty())
            return "Endereço é obrigatório.";
        return null; // tudo válido
    }
}
