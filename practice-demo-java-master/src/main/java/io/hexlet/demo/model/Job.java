package io.hexlet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    private Integer id;
    private String profession;
    private String organization;
    private Integer salary;
    private Date startWorkAt;
}
