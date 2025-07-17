package com.imunegestao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene cenaLogin;
    Stage janela;
    @Override
    public void start(Stage stage) throws Exception {
        janela = stage;
        Parent telaLogin = FXMLLoader.load(getClass().getResource("/com/imunegestao/views/Scene_Login.fxml"));
        cenaLogin = new Scene(telaLogin);
        //sceneLogin.getStylesheets().add(getClass().getResource("/gestaoImune/style.css").toExternalForm());
        janela.setScene(cenaLogin);
        janela.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    public static Scene getCenaLogin() {
        return cenaLogin;
    }
}
