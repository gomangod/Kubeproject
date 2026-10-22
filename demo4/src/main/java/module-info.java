module org.example.demo4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires com.github.dockerjava.core;
    requires com.github.dockerjava.api;
    requires com.github.dockerjava.transport;
    requires com.github.dockerjava.transport.httpclient5;
    requires org.slf4j;

    opens org.example.demo4 to javafx.fxml;
    exports org.example.demo4;
}