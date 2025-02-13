package io.hexlet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class Purchase {
    private Integer id;
    private Date purchaseAt;
    private Member member;
    private String productOrServiceName;
    private Integer quantity;
}
