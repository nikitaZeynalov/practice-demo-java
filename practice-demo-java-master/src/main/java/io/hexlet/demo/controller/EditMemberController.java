package io.hexlet.demo.controller;

import io.hexlet.demo.Window;
import io.hexlet.demo.model.Job;
import io.hexlet.demo.model.Member;
import io.hexlet.demo.repository.FamilyMembersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

import static io.hexlet.demo.ErrorViewer.showError;

@RequiredArgsConstructor
public class EditMemberController {
    private final Member member;

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
        fullName.setText(member.getFullName());
        birthday.setValue(member.getBirthday().toLocalDate());
        fullName.setDisable(true);
        birthday.setDisable(true);
        Job memberJob = member.getJob();
        if (memberJob != null) {
            profession.setText(memberJob.getProfession());
            organization.setText(memberJob.getOrganization());
            salary.setText(memberJob.getSalary().toString());
        }
        saveButton.setOnMouseClicked(mouseEvent -> {
            onSaveClick();
            Window.refreshMain();
        });
    }

    private void onSaveClick() {
        try {
            member.setFullName(getFullName());
            member.setBirthday(Date.valueOf(birthday.getValue()));
            Job job;
            if (isNewJob()) {
                job = new Job();
                job.setStartWorkAt(Date.valueOf(LocalDate.now()));
                job.setProfession(profession.getText());
                job.setOrganization(organization.getText());
            } else {
                job = member.getJob();
            }
            job.setSalary(getSalary());
            member.setJob(job);
            FamilyMembersRepository.update(member);
        } catch (NumberFormatException e) {
            showError(e.getMessage(), "Ошибка в поле месячного дохода");
        } catch (NullPointerException e) {
            showError(e.getMessage(), "Ошибка в поле ФИО");
        }
    }

    private String getFullName() throws NullPointerException {
        if (fullName.getText().isBlank()) {
            throw new NullPointerException("ФИО не может быть пустым.");
        }
        return fullName.getText();
    }

    private boolean isNewJob() {
        Job job = member.getJob();
        boolean isNewProfession;
        boolean isNewOrganization;
        if (job == null) {
            isNewProfession = !profession.getText().isBlank();
            isNewOrganization = !organization.getText().isBlank();
        } else {
            isNewProfession = !profession.getText().equals(job.getProfession());
            isNewOrganization = !organization.getText().equals(job.getOrganization());
        }
        return isNewProfession || isNewOrganization;
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
