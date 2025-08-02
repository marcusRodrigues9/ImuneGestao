package com.imunegestao.repository;

import java.util.HashMap;
import java.util.Map;

import com.imunegestao.models.pessoas.Cidadao;

public class RepositorioCidadao {

    private static RepositorioCidadao instancia;
    private Map<Integer, Cidadao> cidadaos = new HashMap<>();
    private int proximoId = 1; // contador de ID automático

    // Construtor privado para impedir que criem novas instâncias
    private RepositorioCidadao() {
    }


    // Método para acessar a única instância
    public static RepositorioCidadao getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioCidadao();
        }
        return instancia;
    }

    //metodo para que sempre que algum cidadao for excluido, o proximo cidadao criado pegue o id de menor valor
    private int encontrarMenorIdDisponivel() {
        int id = 1;
        while (cidadaos.containsKey(id)) {
            id++;
        }
        return id;
    }

    public void adicionarCidadao(Cidadao cidadao) {
        int novoId = encontrarMenorIdDisponivel();
        cidadao.setId(novoId);
        cidadaos.put(novoId, cidadao);
    }

    public Map<Integer, Cidadao> listarCidadaos() {
        return cidadaos;
    }


    public Cidadao buscarCidadaoPorId(int id) {
        return cidadaos.get(id);
    }
    public Cidadao buscarCidadaoPorCpf(String cpf) {
        return cidadaos.values().stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
}
 /*  public boolean adicionarVacinaACidadao(int idCidadao, int idVacina) {
        Cidadao cidadao = buscarCidadaoPorId(idCidadao);
        if (cidadao != null) {
            if (!cidadao.getIdsVacinasTomadas().contains(idVacina)) {
                cidadao.adicionarVacinaTomada(idVacina);

                RepositorioVacina repVacina = RepositorioVacina.getInstancia(); // Acessa o repositório de vacinas
                Vacina vacina = repVacina.listarVacinas().get(idVacina); // Busca a vacina pelo ID
                String nomeVacina = (vacina != null) ? vacina.getNome() : "Vacina Desconhecida";
                System.out.println("Vacina '" + nomeVacina + "' (ID: " + idVacina + ") foi adicionada ao cidadão '" + cidadao.getNome() + "' (ID: " + cidadao.getId() + ").");

                return true;
            } else {
                System.out.println("Vacina (ID: " + idVacina + ") já registrada para o cidadão '" + cidadao.getNome() + "' (ID: " + cidadao.getId() + ").");
                return false;
            }
        } else {
            System.out.println("Cidadão com ID " + idCidadao + " não encontrado.");
            return false;
        }
    }
    public boolean removerVacinaDeCidadao(int idCidadao, int idVacina) {
        Cidadao cidadao = buscarCidadaoPorId(idCidadao);
        if (cidadao != null) {
            return cidadao.getIdsVacinasTomadas().remove(Integer.valueOf(idVacina)); // Remove o objeto Integer
        } else {
            System.out.println("Cidadão com ID " + idCidadao + " não encontrado.");
            return false;
        }
    }
  

    public List<Integer> listarVacinasDeCidadao(int idCidadao) {
        Cidadao cidadao = buscarCidadaoPorId(idCidadao);
        if (cidadao != null) {
            return cidadao.getIdsVacinasTomadas();
        }
        return null;
    }
}

*/
  

