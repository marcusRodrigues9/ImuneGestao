package com.imunegestao.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SceneLoginController {
    @FXML
    private Button botao_entrar;

    @FXML
    private TextField input_senha;

    @FXML
    private TextField input_usuario;

    public String validarLogin(String username,String password){
        if (username.isEmpty() || password.isEmpty()) return "Preencha todos os campos.";
        if (!username.equals("admin") || !password.equals("admin")) return "Usuário ou senha incorretos.";
        return null; // sem erros de login
    }
    @FXML
    void realizar_login(ActionEvent event) {
        String usuario = input_usuario.getText();
        String senha = input_senha.getText();
        String erro = validarLogin(usuario, senha);

        if(erro != null){
            Alert alertaErro = new Alert(Alert.AlertType.ERROR);
            alertaErro.setHeaderText(erro);
            alertaErro.show();
        }else{

        }
    }
}
