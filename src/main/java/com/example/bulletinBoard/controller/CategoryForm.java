package com.example.bulletinBoard.controller;

import com.example.bulletinBoard.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryForm {

    private String name;
    private String description;
    private Long parentId;

    public Category toEntity() {
        return new Category(name, description);
    }
}
