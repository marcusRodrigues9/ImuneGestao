package com.imunegestao.controllers;

import com.imunegestao.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneAgendamentoController extends BaseController {
    @FXML
    private MenuItem botao_menu_cadastrar_agendamento;

    @FXML
    private MenuItem botao_menu_visualizar_agendamento;

    @FXML
    private Button botao_sair;

    @FXML
    private TextField buscar_agendamento;

    @FXML
    private AnchorPane formulario_agendamento;

    @FXML
    private Button realizar_cadastro_agendamento;

    @FXML
    private TableView<?> tabela_agendamento;

    @FXML
    private AnchorPane tela_agendamento;



    @FXML
    void mostrar_formulario_agendamento(ActionEvent event) {
        mostrarTela(formulario_agendamento, tela_agendamento);
    }

    @FXML
    void mostrar_tabela_agendamento(ActionEvent event) {
        mostrarTela(tela_agendamento, formulario_agendamento);

    }

    @FXML
    void alterar_tela_cidadao(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Cidadao.fxml", "Cidadãos");
    }
    @FXML
    void alterar_tela_vacina(ActionEvent event) throws IOException {
        trocarCena(event, "/com/imunegestao/views/Scene_Visualizar_Vacinas.fxml", "Vacinas");
    }
    @FXML
    public void sair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }
}
