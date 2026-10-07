module org.example.demo4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.demo4 to javafx.fxml;
    exports org.example.demo4;
}