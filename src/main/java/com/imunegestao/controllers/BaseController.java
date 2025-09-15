package com.imunegestao.controllers;

import com.imunegestao.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.DialogPane; // Adicione esta importação
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
// Não precisa importar com.imunegestao.Main aqui se você usar BaseController.class
// para carregar o style.css nos métodos static.

public abstract class BaseController {
    protected void mostrarTela(AnchorPane telaParaMostrar, AnchorPane telaParaEsconder) {
        telaParaMostrar.setVisible(true);
        telaParaEsconder.setVisible(false);
    }
    protected void alternarVisibilidadeTela(VBox telaParaMostrar){
        if (telaParaMostrar.isVisible()){
            telaParaMostrar.setVisible(false);
        }else{ telaParaMostrar.setVisible(true);
        }

    }
    protected void trocarCena(ActionEvent event, String fxmlPath, String titulo) throws IOException {
        Parent novaTela = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene novaCena = new Scene(novaTela);
        novaCena.getStylesheets().add(getClass().getResource("/com/imunegestao/style.css").toExternalForm());

        // Como o source é um MenuItem, não é Node, use getParentPopup()
        MenuItem menuItem = (MenuItem) event.getSource();
        Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();

        stage.setScene(novaCena);
        stage.setTitle(titulo);
        stage.show();
    }
    protected void sair(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(Main.getCenaLogin());
    }

    public static void mostrarAlertaInformacao(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informações");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        // Adicionar o stylesheet ao DialogPane do alerta
        DialogPane dialogPane = alerta.getDialogPane();
        // Carrega o SEU CSS customizado. Usamos BaseController.class para métodos estáticos.
        dialogPane.getStylesheets().add(BaseController.class.getResource("/com/imunegestao/style.css").toExternalForm());
        // Carrega o CSS PADRÃO do JavaFX para dialogs. Não usa getResource() para este.
        dialogPane.getStylesheets().add("dialog.css");

        alerta.showAndWait();
    }

    public static void mostrarAlertaErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        // Adicionar o stylesheet ao DialogPane do alerta
        DialogPane dialogPane = alerta.getDialogPane();
        // Carrega o SEU CSS customizado. Usamos BaseController.class para métodos estáticos.
        dialogPane.getStylesheets().add(BaseController.class.getResource("/com/imunegestao/style.css").toExternalForm());
        // Carrega o CSS PADRÃO do JavaFX para dialogs. Não usa getResource() para este.
        dialogPane.getStylesheets().add("dialog.css");

        alerta.showAndWait();
    }
}