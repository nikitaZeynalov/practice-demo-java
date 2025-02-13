package io.hexlet.demo.controller;

import io.hexlet.demo.Window;
import io.hexlet.demo.repository.FamilyMembersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainController {
    @FXML
    private Pane root;
    @FXML
    private VBox vbFamilyUsers;
    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        ImageView imageView = new ImageView("Лого.png");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(64);
        imageView.setX(40);
        imageView.setY(20);
        root.getChildren().add(imageView);
        FamilyMembersRepository.getAll().forEach(member -> {
            FamilyMemberElementController elementController = new FamilyMemberElementController(member);
            var element = Window.Element.getParent("family-list-element-view.fxml", elementController);
            element.setOnMouseClicked(mouseEvent -> {
                EditMemberController controller = new EditMemberController(member);
                new Window("family-member-panel-view.fxml", controller, "Редактирование члена семьи").show();
            });
            vbFamilyUsers.getChildren().add(element);
            vbFamilyUsers.setBackground(Background.fill(Color.valueOf("#EBE84B")));
        });
        addButton.setOnMouseClicked(mouseEvent -> {
            AddMemberController controller = new AddMemberController();
            new Window("family-member-panel-view.fxml", controller, "Добавление члена семьи").show();
        });
    }
}