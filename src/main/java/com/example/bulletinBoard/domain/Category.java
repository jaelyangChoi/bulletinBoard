package com.example.bulletinBoard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();

    /*셀프 연관관계 매핑 => 이름만 내거지 다른 연관관계와 같다고 생각하면 된다*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") //FK 이름 지정
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    //==양방향 연관관계 편의 메서드==//
    public void addChildCategory(Category child) {
        this.children.add(child);
        child.setParent(this);
    }
}
