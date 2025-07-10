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
    private MenuItem botao_menu_visualizar;

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
        formulario_vacina.setVisible(true);
        tela_vacina.setVisible(false);
    }

    @FXML
    void mostrar_tabela_vacina(ActionEvent event) {
        formulario_vacina.setVisible(false);
        tela_vacina.setVisible(true);
    }

    @FXML
    void alterar_tela_visualizacao_cidadao(ActionEvent event) throws IOException {
        Parent telaCidadaos = FXMLLoader.load(getClass().getResource("/com/imunegestao/views/Scene_Visualizar_Cidadao.fxml"));
        Scene CenaCidadaos = new Scene(telaCidadaos);

        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stage.setScene(CenaCidadaos);
        stage.setTitle("Cidadaos");
        stage.show();
    }

    @FXML
    void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }
}
