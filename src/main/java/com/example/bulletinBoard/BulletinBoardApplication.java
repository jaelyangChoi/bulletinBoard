package com.example.bulletinBoard;

import com.example.bulletinBoard.domain.category.CategoryJpaRepository;
import com.example.bulletinBoard.domain.member.MemberJpaRepository;
import com.example.bulletinBoard.domain.post.PostJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
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

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("temp");
    }

}
