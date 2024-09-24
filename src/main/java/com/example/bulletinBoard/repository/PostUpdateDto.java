package com.example.bulletinBoard.repository;

import com.example.bulletinBoard.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PostUpdateDto {
    private String title;
    private String content;
    private Character secretYN;
    private Category category;
}
