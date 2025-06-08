package com.imunegestao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage janela;
    @Override
    public void start(Stage stage) throws Exception {
        janela = stage;
        Parent telaLogin = FXMLLoader.load(getClass().getResource("/com/imunegestao/views/Scene_Login.fxml"));
        Scene sceneLogin = new Scene(telaLogin);
      //  sceneLogin.getStylesheets().add(getClass().getResource("/gestaoImune/style.css").toExternalForm());
        janela.setScene(sceneLogin);
        janela.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
