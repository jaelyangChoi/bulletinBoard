package com.example.bulletinBoard.domain.post;

import com.example.bulletinBoard.domain.category.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostSearch {
    private String title;
    private String authorName;
    private Category category;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
