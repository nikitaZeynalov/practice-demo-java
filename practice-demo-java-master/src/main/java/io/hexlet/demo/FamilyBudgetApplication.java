package io.hexlet.demo;

import javafx.application.Application;
import javafx.stage.Stage;

public class FamilyBudgetApplication extends Application {
    private DatabaseManager databaseManager;

    @Override
    public void start(Stage stage) {
        databaseManager = new DatabaseManager();
        databaseManager
                .openConnection()
                .initialize();
        new Window("main-view.fxml", null, "Члены семьи").show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        databaseManager.closeConnection();
    }
}