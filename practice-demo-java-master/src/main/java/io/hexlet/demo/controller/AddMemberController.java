package io.hexlet.demo.controller;

import io.hexlet.demo.Window;
import io.hexlet.demo.model.Job;
import io.hexlet.demo.model.Member;
import io.hexlet.demo.repository.FamilyMembersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

import static io.hexlet.demo.ErrorViewer.showError;

public class AddMemberController {
    @FXML
    private TextField fullName;
    @FXML
    private DatePicker birthday;
    @FXML
    private TextField profession;
    @FXML
    private TextField organization;
    @FXML
    private TextField salary;
    @FXML
    private Button saveButton;

    @FXML
    private void initialize() {
        saveButton.setOnMouseClicked(mouseEvent -> {
            onSaveClick();
            Window.refreshMain();
        });
    }

    private void onSaveClick() {
        try {
            Member newMember = new Member(getFullName(), Date.valueOf(birthday.getValue()));
            if (isNewJob()) {
                Job job = new Job();
                job.setStartWorkAt(Date.valueOf(LocalDate.now()));
                job.setProfession(profession.getText());
                job.setOrganization(organization.getText());
                job.setSalary(getSalary());
                newMember.setJob(job);
            }
            FamilyMembersRepository.create(newMember);
        } catch (NumberFormatException e) {
            showError(e.getMessage(), "Ошибка в поле месячного дохода");
        } catch (NullPointerException e) {
            showError(e.getMessage(), "Ошибка в поле ФИО");
        }
    }

    private boolean isNewJob() {
        boolean isNewProfession = !profession.getText().isBlank();
        boolean isNewOrganization = !organization.getText().isBlank();
        return isNewProfession || isNewOrganization;
    }

    private String getFullName() throws NullPointerException {
        if (fullName.getText().isBlank()) {
            throw new NullPointerException("ФИО не может быть пустым.");
        }
        return fullName.getText();
    }

    private int getSalary() throws NumberFormatException {
        int result;
        try {
            result = Integer.parseInt(String.valueOf(salary.getText()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Текущий месячный доход должен быть числом.");
        }
        if (result < 0) {
            throw new NumberFormatException("Текущий месячный доход не может быть отрицательным числом.");
        }
        return result;
    }
}
