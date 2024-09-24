package com.example.bulletinBoard;

import com.example.bulletinBoard.repository.CategoryRepository;
import com.example.bulletinBoard.repository.MemberRepository;
import com.example.bulletinBoard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
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
    public TestDataInit testDataInit(MemberRepository memberRepository, CategoryRepository categoryRepository, PostRepository postRepository) {
        return new TestDataInit(memberRepository, categoryRepository, postRepository);
    }
}
