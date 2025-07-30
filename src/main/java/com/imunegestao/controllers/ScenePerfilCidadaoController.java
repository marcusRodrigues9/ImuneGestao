package com.imunegestao.controllers;

import com.imunegestao.Main;
import com.imunegestao.models.pessoas.Cidadao;
import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.repository.RepositorioCidadao;
import com.imunegestao.repository.RepositorioVacina;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ScenePerfilCidadaoController extends BaseController {

    // --- Variável de Instância ---
    private Cidadao cidadaoAtual; // Variável para armazenar o cidadão que está sendo visualizado

    // --- Componentes FXML (conectados ao seu Scene_Perfil_Cidadao.fxml) ---
    @FXML private Label labelNome;
    @FXML private Label labelCpf;
    @FXML private Label labelEndereco;
    @FXML private Label labelEmail;
    @FXML private Label labelTelefone;
    @FXML private Label labelSexo;
    @FXML private Label labelIdade;


    @FXML
    public void initialize() {

    }


    public void setCidadao(Cidadao cidadao) {
        this.cidadaoAtual = cidadao; // Armazena o cidadão recebido
        preencherDadosCidadao();    // Preenche os Labels com os dados
    }

    // Preenche os Labels na tela com os dados do cidadão atual
    private void preencherDadosCidadao() {
        if (cidadaoAtual != null) {
            labelNome.setText(cidadaoAtual.getNome());
            labelCpf.setText(cidadaoAtual.getCpf());
            labelEndereco.setText(cidadaoAtual.getEndereco());
            labelEmail.setText(cidadaoAtual.getNumeroTelefone()); // Era 'Email', ajustado para 'NumeroTelefone' se for o caso
            labelTelefone.setText(cidadaoAtual.getEmail()); // Era 'Telefone', ajustado para 'Email' se for o caso
            labelSexo.setText(cidadaoAtual.getSexo());
            labelIdade.setText(String.valueOf(cidadaoAtual.getIdade())); // Converte int para String
        } else {
            // Se por algum motivo o cidadão for nulo, limpe os campos ou mostre uma mensagem padrão
            labelNome.setText("N/A");
            labelCpf.setText("N/A");
            labelEndereco.setText("N/A");
            labelEmail.setText("N/A");
            labelTelefone.setText("N/A");
            labelSexo.setText("N/A");
            labelIdade.setText("N/A");
        }
    }

    // --- Métodos de Navegação (se necessário, copie do SceneCidadaoController ou BaseController) ---
    @FXML
    private void voltarParaCidadaos(ActionEvent event) {
        // Este método só será necessário se você tiver um botão "Voltar" no seu Scene_Perfil_Cidadao.fxml
        try {
            trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Cidadao.fxml", "Cidadãos");
        } catch (IOException e) {
            e.printStackTrace();
            // Use o seu método de alerta de erro, como você ajustou no SceneCidadaoController
            mostrarAlertaErro("Erro ao voltar para a tela de cidadãos.");
        }
    }

    @FXML
    private void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }
}
