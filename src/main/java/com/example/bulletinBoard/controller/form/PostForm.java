package com.example.bulletinBoard.controller.form;

import com.example.bulletinBoard.domain.Category;
import com.example.bulletinBoard.domain.Member;
import com.example.bulletinBoard.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostForm {
    private String title;
    private String content;
    private Long categoryId;
    private Character secretYN;

    public Post toEntity(Member member, Category category) {
        return new Post(title, content, member, category, secretYN);
    }

    public static PostForm fromEntity(Post post) {
        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        postForm.setSecretYN(post.getSecretYN());
        postForm.setCategoryId(post.getCategory().getId());
        return postForm;
    }
}
