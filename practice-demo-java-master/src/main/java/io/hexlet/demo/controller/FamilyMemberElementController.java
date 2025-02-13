package io.hexlet.demo.controller;

import io.hexlet.demo.model.Job;
import io.hexlet.demo.model.Member;
import io.hexlet.demo.repository.FamilyMembersRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public class FamilyMemberElementController {
    private final Member member;

    @FXML
    private BorderPane container;
    @FXML
    private Label fullName;
    @FXML
    private Label age;
    @FXML
    private Label profession;
    @FXML
    private Label organization;
    @FXML
    private Label salary;
    @FXML
    private Label coefficient;

    @FXML
    private void initialize() {
        fullName.setText(member.getFullName());
        age.setText(getAge());
        Job memberJob = member.getJob();
        if (memberJob == null) {
            profession.setText("Безработный");
            organization.setText("----");
            salary.setText("0");
            coefficient.setText("0");
        } else {
            profession.setText(memberJob.getProfession());
            organization.setText(memberJob.getOrganization());
            salary.setText(memberJob.getSalary().toString());
            coefficient.setText(FamilyMembersRepository.spendingToIncomeRatio(member));
        }
        Border stock = Border.stroke(Color.BLACK);
        container.setOnMouseEntered(mouseEvent -> {
            container.setBorder(Border.stroke(Paint.valueOf("#B11AED")));
        });
        container.setOnMouseExited(mouseEvent -> {
            container.setBorder(stock);
        });
    }

    private String getAge() {
        var age = ChronoUnit.YEARS.between(member.getBirthday().toLocalDate(), LocalDate.now());
        return String.valueOf(age);
    }
}
