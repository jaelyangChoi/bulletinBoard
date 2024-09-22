package com.example.bulletinBoard.domain;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String password; //추후 암호화 저장 기능 구현 필

    @Enumerated(EnumType.STRING)
    private MemberRole role;
}
