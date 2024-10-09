package com.example.bulletinBoard.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PostUpdateDto {
    private String title;
    private String content;
    private Character secretYN;
}
