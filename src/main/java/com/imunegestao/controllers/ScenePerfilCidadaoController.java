package com.imunegestao.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.imunegestao.Main;
import com.imunegestao.models.RegistroVacina;
import com.imunegestao.models.pessoas.Cidadao;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ScenePerfilCidadaoController extends BaseController {
    // --- Variável de Instância ---
    private Cidadao cidadaoAtual; // Variável para armazenar o cidadão que está sendo visualizado

    // --- Componentes FXML ---
    @FXML private Label labelNome;
    @FXML private Label labelCpf;
    @FXML private Label labelEndereco;
    @FXML private Label labelEmail;
    @FXML private Label labelTelefone;
    @FXML private Label labelSexo;
    @FXML private Label labelIdade;


    @FXML private TableView<RegistroVacina> tabela_historico;

    @FXML private TableColumn<RegistroVacina, LocalDate> coluna_data;
    @FXML private TableColumn<RegistroVacina, String> coluna_vacina;
    @FXML private TableColumn<RegistroVacina, String> coluna_fabricante;
    //@FXML private TableColumn<RegistroVacina, String> coluna_funcionario;

    private ObservableList<RegistroVacina> listaDeVacinasTomadas;

    @FXML
    public void initialize() {
        // Inicializa a lista e a tabela
        listaDeVacinasTomadas = FXCollections.observableArrayList();
        tabela_historico.setItems(listaDeVacinasTomadas);

        // Configura as colunas
        coluna_data.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDataAplicacao()));
        coluna_vacina.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVacina().getNome()));
        coluna_fabricante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVacina().getFabricante()));
        //coluna_funcionario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeFuncionario()));

    }


    public void setCidadao(Cidadao cidadao) {
        this.cidadaoAtual = cidadao; // Armazena o cidadão recebido
        preencherDadosCidadao();    // Preenche os Labels com os dados
        carregarVacinasDoCidadao();
    }
    private void carregarVacinasDoCidadao() {
        if (cidadaoAtual != null) {
            listaDeVacinasTomadas.clear();
            listaDeVacinasTomadas.addAll(cidadaoAtual.getVacinasTomadas());
        }
    }


    private void preencherDadosCidadao() {
        if (cidadaoAtual != null) {
            labelNome.setText(cidadaoAtual.getNome());
            labelCpf.setText(cidadaoAtual.getCpf());
            labelEndereco.setText(cidadaoAtual.getEndereco());
            labelEmail.setText(cidadaoAtual.getNumeroTelefone());
            labelTelefone.setText(cidadaoAtual.getEmail());
            labelSexo.setText(cidadaoAtual.getSexo());
            labelIdade.setText(String.valueOf(cidadaoAtual.getIdade())); // Converte int para String
        } else {
            // Se por algum motivo o cidadão for nulo.
            labelNome.setText("N/A");
            labelCpf.setText("N/A");
            labelEndereco.setText("N/A");
            labelEmail.setText("N/A");
            labelTelefone.setText("N/A");
            labelSexo.setText("N/A");
            labelIdade.setText("N/A");
        }
    }


    @FXML
    private void voltarParaCidadaos(ActionEvent event) {

        try {
            trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Cidadao.fxml", "Cidadãos");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao voltar para a tela de cidadãos.");
        }
    }

    @FXML
    public void sair(ActionEvent event){
        super.sair(event);
    }
}
