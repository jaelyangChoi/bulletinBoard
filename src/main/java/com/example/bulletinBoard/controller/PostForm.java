package com.example.bulletinBoard.controller;

import com.example.bulletinBoard.domain.Category;
import com.example.bulletinBoard.domain.Member;
import com.example.bulletinBoard.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostForm {
    private String title;
    private String content;
    private Long authorId;
    private Long categoryId;
    private Character secretYN;

    public Post toEntity(Member member, Category category) {
        return new Post(title, content, member, category, secretYN);
    }
}
