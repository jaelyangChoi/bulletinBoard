package com.example.bulletinBoard.web.post;

import com.example.bulletinBoard.domain.category.Category;
import com.example.bulletinBoard.domain.member.Member;
import com.example.bulletinBoard.domain.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class PostForm {
    @NotBlank
    @Length(max = 15)
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Long categoryId;
    private Character secretYN = 'N';

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
