module com.imunegestao.imunegestao {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.desktop;


    opens com.imunegestao to javafx.fxml;
    opens com.imunegestao.controllers to javafx.fxml;



    opens com.imunegestao.models.pessoas to com.google.gson, javafx.base;
    opens com.imunegestao.models.vacinas to com.google.gson, javafx.base;
    opens com.imunegestao.models.agendamento to com.google.gson, javafx.base;


    opens com.imunegestao.models to com.google.gson;

    opens com.imunegestao.models.enums to com.google.gson;

    exports com.imunegestao;
}