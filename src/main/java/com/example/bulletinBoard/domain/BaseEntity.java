package com.example.bulletinBoard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
    @Column(updatable = false)
    private String createdBy;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String updatedBy;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();  // 생성 시 시간 자동 설정
        this.updatedAt = LocalDateTime.now();  // 생성 시 업데이트 시간도 동일하게 설정
        /*Spring Security 활용해 생성자 자동 입력 부 개발 필*/
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();  // 업데이트 시 시간 자동 설정
    }
}
