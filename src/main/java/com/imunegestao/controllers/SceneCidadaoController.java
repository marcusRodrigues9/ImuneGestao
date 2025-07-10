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

public class SceneCidadaoController extends BaseController {
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
    void mostrar_formulario_cidadao(ActionEvent event) {
        mostrarTela(formulario_cidadao, tela_cidadao);
    }

    @FXML
    void mostrar_tabela_cidadao(ActionEvent event) {
        mostrarTela(tela_cidadao, formulario_cidadao);
    }
    @FXML
    void alterar_tela_visualizacao_vacina(ActionEvent event) throws IOException {

        Parent telaVacinas = FXMLLoader.load(getClass().getResource("/com/imunegestao/views/Scene_Visualizar_Vacinas.fxml"));
        Scene cenaVacinas = new Scene(telaVacinas);

        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stage.setScene(cenaVacinas);
        stage.setTitle("Cidadaos");
        stage.show();
    }
    @FXML
    public void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }
}
