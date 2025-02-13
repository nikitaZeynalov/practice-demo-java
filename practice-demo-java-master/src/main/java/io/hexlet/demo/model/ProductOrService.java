package io.hexlet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductOrService {
    private String name;
    private String category;
    private Integer price;
}
