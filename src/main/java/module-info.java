module com.imunegestao.imunegestao {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.imunegestao to javafx.fxml;
    opens com.imunegestao.controllers to javafx.fxml;
    opens com.imunegestao.models.pessoas to javafx.base;
    opens com.imunegestao.models.vacinas to javafx.base;


    exports com.imunegestao;
}