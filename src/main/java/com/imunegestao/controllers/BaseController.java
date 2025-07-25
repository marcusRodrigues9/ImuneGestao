package com.imunegestao.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {
    protected void mostrarTela(AnchorPane telaParaMostrar, AnchorPane telaParaEsconder) {
        telaParaMostrar.setVisible(true);
        telaParaEsconder.setVisible(false);
    }
    protected void trocarCena(ActionEvent event, String fxmlPath, String titulo) throws IOException {
        Parent novaTela = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene novaCena = new Scene(novaTela);

        // Como o source é um MenuItem, não é Node, use getParentPopup()
        MenuItem menuItem = (MenuItem) event.getSource();
        Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();

        stage.setScene(novaCena);
        stage.setTitle(titulo);
        stage.show();
    }

    public static void mostrarAlertaInformacao(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informações");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void mostrarAlertaErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}