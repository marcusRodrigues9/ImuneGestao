package com.imunegestao.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoginController {
    @FXML
    private Button botao_entrar;

    @FXML
    private PasswordField input_senha;

    @FXML
    private TextField input_usuario;

    public String validarLogin(String username,String password){
        if (username.isEmpty() || password.isEmpty()) return "Preencha todos os campos.";
        if (!username.equals("admin") || !password.equals("admin")) return "Usuário ou senha incorretos.";
        return null; // sem erros de login
    }
    @FXML
    void realizar_login(ActionEvent event) throws IOException {
        String usuario = input_usuario.getText();
        String senha = input_senha.getText();
        String erro = validarLogin(usuario, senha);

        if(erro != null){
            Alert alertaErro = new Alert(Alert.AlertType.ERROR);
            alertaErro.setHeaderText(erro);
            alertaErro.show();
            return;
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Login realizado com sucesso!");
            alert.showAndWait();

            Parent telaPacientes = FXMLLoader.load(getClass().getResource("/com/imunegestao/views/scene-visualizar-paciente.fxml"));
            Scene CenaPacientes = new Scene(telaPacientes);
            CenaPacientes.getStylesheets().add(getClass().getResource("/com/imunegestao/style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(CenaPacientes);
            stage.setTitle("Pacientes");
            stage.show();
        }
    }
    @FXML
    public void initialize() {

        // Botão Enter Faz a mesma função do TAB
        input_usuario.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                input_senha.requestFocus();
            }
        });

        input_senha.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                botao_entrar.requestFocus();
            }
        });
    }
}
