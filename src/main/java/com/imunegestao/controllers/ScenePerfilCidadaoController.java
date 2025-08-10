package com.imunegestao.controllers;

import java.io.IOException;

import com.imunegestao.Main;
import com.imunegestao.models.pessoas.Cidadao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
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

    //colunas tabela
    @FXML TableColumn<?, ?> coluna_data;
    @FXML
    private TableColumn<?, ?> coluna_fabricante;

    @FXML
    private TableColumn<?, ?> coluna_funcionario;

    @FXML
    private TableColumn<?, ?> coluna_vacina;

    @FXML
    public void initialize() {

    }


    public void setCidadao(Cidadao cidadao) {
        this.cidadaoAtual = cidadao; // Armazena o cidadão recebido
        preencherDadosCidadao();    // Preenche os Labels com os dados
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
