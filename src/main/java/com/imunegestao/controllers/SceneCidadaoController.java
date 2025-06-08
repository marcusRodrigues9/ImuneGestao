package com.imunegestao.controllers;

import com.imunegestao.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneCidadaoController {
    @FXML
    private Button botao_sair;
    @FXML
    private MenuItem botao_menu_cadastrar;

    @FXML
    private MenuItem botao_menu_visualizar;

    @FXML
    private TextField buscar_cidadao;

    @FXML
    private AnchorPane formulario_cidadao;

    @FXML
    private TableView<?> tabela_cidadaos;

    @FXML
    private AnchorPane tela_cidadao;

    @FXML
    void alterar_tela_cadastro(ActionEvent event) {
        formulario_cidadao.setVisible(true);
        tela_cidadao.setVisible(false);
    }

    @FXML
    void alterar_tela_visualizacao(ActionEvent event) {
        formulario_cidadao.setVisible(false);
        tela_cidadao.setVisible(true);
    }
    @FXML
    public void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }
}
