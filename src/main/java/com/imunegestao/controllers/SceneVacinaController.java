package com.imunegestao.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SceneVacinaController {

    @FXML
    private MenuItem botao_menu_cadastrar_vacina;

    @FXML
    private MenuItem botao_menu_visualizar;

    @FXML
    private MenuItem botao_menu_visualizar_vacina;

    @FXML
    private Button botao_sair;

    @FXML
    private TextField buscar_cidadao;

    @FXML
    private AnchorPane formulario_vacina;

    @FXML
    private TableView<?> tabela_vacinas;

    @FXML
    private AnchorPane tela_vacina;

    @FXML
    void alterar_tela_cadastro_vacina(ActionEvent event) {
        formulario_vacina.setVisible(true);
        tela_vacina.setVisible(false);
    }

    @FXML
    void alterar_tela_visualizacao(ActionEvent event) {
        formulario_vacina.setVisible(false);
        tela_vacina.setVisible(true);
    }

    @FXML
    void alterar_tela_visualizacao_vacina(ActionEvent event) {

    }

    @FXML
    void sair(ActionEvent event) {

    }
}
