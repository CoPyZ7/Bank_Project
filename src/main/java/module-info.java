module com.example.bank_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.bank_project to javafx.fxml;
    exports com.example.bank_project;
}