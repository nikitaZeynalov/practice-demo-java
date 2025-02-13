package io.hexlet.demo;

import javafx.scene.control.Alert;

public class ErrorViewer {
    public static void showError(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle("Ошибочные данные формы");
        alert.setHeaderText(header);
        alert.show();
    }
}
