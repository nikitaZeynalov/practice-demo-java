package io.hexlet.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Window {
    private static final List<Window> windows = new ArrayList<>();
    private final Stage stage = new Stage();

    private final String fxml;
    @Getter
    private final String title;

    public Window(String fxml, Object controller, String title) {
        this.fxml = fxml;
        this.title = title;
        FXMLLoader fxmlLoader = new FXMLLoader(Window.class.getResource(fxml));
        Scene scene;
        try {
            if (controller != null) {
                fxmlLoader.setController(controller);
            }
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.setTitle(title);
        if (isMainWindow()) {
            stage.setOnCloseRequest(windowEvent -> windows.forEach(Window::close));
        }
    }

    public void show() {
        stage.show();
        windows.add(this);
    }

    public void refresh() {
        try {
            var loader = new FXMLLoader(Window.class.getResource(fxml));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    private void close() {
        stage.close();
    }

    public static void refreshMain() {
        windows.stream()
                .filter(Window::isMainWindow)
                .findFirst()
                .ifPresent(Window::refresh);
    }

    private boolean isMainWindow() {
        return stage.getTitle().equals("Члены семьи");
    }

    public static class Element {
        public static Parent getParent(String fxml, Object controller) {
            FXMLLoader fxmlLoader = new FXMLLoader(Window.class.getResource(fxml));
            fxmlLoader.setController(controller);
            try {
                return fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}