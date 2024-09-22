package com.example.bulletinBoard.domain;

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
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /*
    //==연관관계 편의 메서드==//
    public void setCategory(Category category) {
        this.category = category;
        category.getPosts().add(this);
    }*/
}
