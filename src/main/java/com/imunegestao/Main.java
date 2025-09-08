package com.imunegestao;

import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.repository.RepositorioVacina;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {
    private static Scene cenaLogin;
    Stage janela;
    @Override
    public void start(Stage stage) throws Exception {

        janela = stage;
        Parent telaLogin = FXMLLoader.load(getClass().getResource("/com/imunegestao/views/scene-login.fxml"));
        cenaLogin = new Scene(telaLogin);

        cenaLogin.getStylesheets().add(getClass().getResource("/com/imunegestao/style.css").toExternalForm());
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
