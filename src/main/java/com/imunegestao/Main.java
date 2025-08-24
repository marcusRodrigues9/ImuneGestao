package com.imunegestao;

import com.imunegestao.models.RegistroVacina;
import com.imunegestao.models.pessoas.Paciente;
import com.imunegestao.models.pessoas.ProfissionalSaude;
import com.imunegestao.models.vacinas.Vacina;
import com.imunegestao.repository.RepositorioPaciente;
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
        inicializarDados();
        janela = stage;
        Parent telaLogin = FXMLLoader.load(getClass().getResource("/com/imunegestao/views/Scene_Login.fxml"));
        cenaLogin = new Scene(telaLogin);
        cenaLogin.getStylesheets().add(getClass().getResource("/com/imunegestao/style.css").toExternalForm());
        janela.setScene(cenaLogin);
        janela.show();
    }

    private void inicializarDados() {
        RepositorioVacina repositorioVacina = RepositorioVacina.getInstancia();
        if (repositorioVacina.listarVacinas().isEmpty()) {
            Vacina v1 = new Vacina("Gripe", "Cimed", 5, 2, LocalDate.now().plusMonths(6));
            Vacina v2 = new Vacina("Covid-19", "BioPharma", 10, 2, LocalDate.now().plusMonths(12));
            repositorioVacina.adicionarVacina(v1);
            repositorioVacina.adicionarVacina(v2);
        }


    }
    public static void main(String[] args) {
        launch(args);
    }
    public static Scene getCenaLogin() {
        return cenaLogin;
    }


}
