module com.imunegestao.imunegestao {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;


    opens com.imunegestao to javafx.fxml;
    opens com.imunegestao.controllers to javafx.fxml;

    exports com.imunegestao;
}