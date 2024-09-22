package com.example.bulletinBoard.controller;

import com.example.bulletinBoard.domain.Member;
import com.example.bulletinBoard.domain.MemberRole;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    private String name;
    private String email;
    private String password;
    private MemberRole role;

    public Member toEntity() {
        this.role = MemberRole.USER;
        return new Member(name, email, password, role);
    }
}
