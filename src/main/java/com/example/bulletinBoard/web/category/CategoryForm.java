package com.example.bulletinBoard.web.category;

import com.example.bulletinBoard.domain.category.Category;
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
