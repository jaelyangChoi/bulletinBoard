package com.example.bulletinBoard;

import com.example.bulletinBoard.domain.category.CategoryJpaRepository;
import com.example.bulletinBoard.domain.member.MemberJpaRepository;
import com.example.bulletinBoard.domain.post.PostJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BulletinBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BulletinBoardApplication.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit testDataInit(MemberJpaRepository memberRepository, CategoryJpaRepository categoryRepository, PostJpaRepository postRepository) {
        return new TestDataInit(memberRepository, categoryRepository, postRepository);
    }
}
