package com.imunegestao.controllers;

import javafx.scene.layout.AnchorPane;

public abstract class BaseController {
    protected void mostrarTela(AnchorPane telaParaMostrar, AnchorPane telaParaEsconder) {
        telaParaMostrar.setVisible(true);
        telaParaEsconder.setVisible(false);
    }
}