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

public class SceneVacinaController extends BaseController {

    @FXML
    private MenuItem botao_menu_cadastrar_vacina;

    @FXML
    private MenuItem botao_menu_visualizar_vacina;

    @FXML
    private Button botao_sair;

    @FXML
    private TextField buscar_vacina;

    @FXML
    private AnchorPane formulario_vacina;

    @FXML
    private TableView<?> tabela_vacinas;

    @FXML
    private AnchorPane tela_vacina;

    @FXML
    void mostrar_formulario_vacina(ActionEvent event) {
        mostrarTela(formulario_vacina, tela_vacina);
    }

    @FXML
    void mostrar_tabela_vacina(ActionEvent event) {
        mostrarTela(tela_vacina, formulario_vacina);
    }

    @FXML
    void alterar_tela_agendamento(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Agendamentos.fxml", "Agendamento");
    }

    @FXML
    void alterar_tela_cidadao(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Cidadao.fxml", "Cidadãos");
    }

    @FXML
    void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }
}
