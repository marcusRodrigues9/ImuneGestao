package com.imunegestao.controllers;

import com.imunegestao.Main;
import com.imunegestao.models.pessoas.Cidadao;
import com.imunegestao.repository.RepositorioCidadao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneCidadaoController extends BaseController {
    private RepositorioCidadao repositorioCidadao = new RepositorioCidadao();
    private ObservableList<Cidadao> listaCidadaos = FXCollections.observableArrayList();
    //==============================================
    @FXML
    private TableView<Cidadao>tabela_cidadaos;
    @FXML
    private TableColumn<Cidadao, String> coluna_cpf_cidadao;
    @FXML
    private TableColumn<Cidadao, String> coluna_endereco_cidadao;
    @FXML
    private TableColumn<Cidadao, Integer> coluna_id_cidadao;
    @FXML
    private TableColumn<Cidadao, Integer> coluna_idade_cidadao;
    @FXML
    private TableColumn<Cidadao, String> coluna_nome_cidadao;
    @FXML
    private TableColumn<Cidadao, String> coluna_sexo_cidadao;
    //==============================================
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
    //==============================================
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
    private AnchorPane tela_cidadao;

    @FXML
    void mostrar_formulario_cidadao(ActionEvent event) {
        mostrarTela(formulario_cidadao, tela_cidadao);
    }
    public void iniciarTabela() {
        System.out.println("Inicializando tabela. Lista de cidadãos: " + listaCidadaos.size());
        coluna_id_cidadao.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluna_nome_cidadao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_cpf_cidadao.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        coluna_idade_cidadao.setCellValueFactory(new PropertyValueFactory<>("idade"));
        coluna_sexo_cidadao.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        coluna_endereco_cidadao.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tabela_cidadaos.setItems(listaCidadaos);
    }
    @FXML
    void mostrar_tabela_cidadao(ActionEvent event) {
        iniciarTabela();
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

    @FXML

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
        listaCidadaos.add(cidadao); // adiciona à tabela!
        mostrarAlertaInformacao("Cidadão cadastrado com sucesso!\n\n"
                + "ID: " + cidadao.getId() + "\n"
                + "Nome: " + cidadao.getNome() + "\n"
                + "CPF: " + cidadao.getCpf() + "\n"
                + "Idade: " + cidadao.getIdade() + "\n"
                + "Sexo: " + cidadao.getSexo() + "\n"
                + "Endereço: " + cidadao.getEndereco());
    }
}
