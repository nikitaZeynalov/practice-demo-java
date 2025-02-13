package io.hexlet.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Member {
    private String fullName;
    private Date birthday;
    private Job job;

    public Member(String fullName, Date birthday) {
        this.fullName = fullName;
        this.birthday = birthday;
    }
}
