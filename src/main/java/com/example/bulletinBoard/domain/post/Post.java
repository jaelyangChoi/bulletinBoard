package com.example.bulletinBoard.domain.post;

import com.example.bulletinBoard.domain.BaseEntity;
import com.example.bulletinBoard.domain.category.Category;
import com.example.bulletinBoard.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Character secretYN;

    protected Post() {
    }

    public Post(String title, String content, Member member, Category category, Character secretYN) {
        this.title = title;
        this.content = content;
        this.author = member;
        this.category = category;
        this.secretYN = secretYN;
    }

    public void update(PostUpdateDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.secretYN = dto.getSecretYN();
    }
    /*
    //==연관관계 편의 메서드==//
    public void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }*/
}
