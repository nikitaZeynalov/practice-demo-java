module io.hexlet.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires static lombok;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens io.hexlet.demo to javafx.fxml;
    exports io.hexlet.demo;
    exports io.hexlet.demo.controller;
    opens io.hexlet.demo.controller to javafx.fxml;
}