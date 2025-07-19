package com.imunegestao.controllers;

import com.imunegestao.Main;
import com.imunegestao.models.pessoas.Cidadao;
import com.imunegestao.repository.RepositorioCidadao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneCidadaoController extends BaseController {
    private RepositorioCidadao repositorioCidadao = new RepositorioCidadao();
    @FXML
    private TextField campo_bairro;
    @FXML
    private TextField campo_cpf;
    @FXML
    private TextField campo_endereco;
    @FXML
    private TextField campo_idade;
    @FXML
    private TextField campo_nome;
    @FXML
    private TextField campo_sexo;

    @FXML
    private Button botao_sair;
    @FXML
    private MenuItem botao_menu_cadastrar_cidadao;
    @FXML
    private MenuItem botao_menu_visualizar_cidadao;

    @FXML
    private TextField buscar_cidadao;

    @FXML
    private AnchorPane formulario_cidadao;

    @FXML
    private TableView<?> tabela_cidadaos;

    @FXML
    private AnchorPane tela_cidadao;

    @FXML
    void mostrar_formulario_cidadao(ActionEvent event) {
        mostrarTela(formulario_cidadao, tela_cidadao);
    }

    @FXML
    void mostrar_tabela_cidadao(ActionEvent event) {
        mostrarTela(tela_cidadao, formulario_cidadao);
    }
    @FXML
    void alterar_tela_vacina(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Vacinas.fxml", "Vacinas");
    }

    @FXML
    void alterar_tela_agendamento(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Agendamentos.fxml", "Agendamento");
    }

    @FXML
    public void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }
    private void mostrarAlertaInformacao(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informações do Cidadão");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public void salvarCidadao(){
        String nome = campo_nome.getText();
        String cpf = campo_cpf.getText();
        String endereco = campo_endereco.getText();
        int idade = Integer.parseInt(campo_idade.getText());
        String sexo = campo_sexo.getText();
        Cidadao cidadao = new Cidadao(nome, cpf, idade, sexo, endereco);
        repositorioCidadao.adicionarCidadao(cidadao);
        mostrarAlertaInformacao("Cidadão cadastrado com sucesso!\n\n"
                + "ID: " + cidadao.getId() + "\n"
                + "Nome: " + cidadao.getNome() + "\n"
                + "CPF: " + cidadao.getCpf() + "\n"
                + "Idade: " + cidadao.getIdade() + "\n"
                + "Sexo: " + cidadao.getSexo() + "\n"
                + "Endereço: " + cidadao.getEndereco());
    }
}
