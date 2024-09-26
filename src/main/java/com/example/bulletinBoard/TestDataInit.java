package com.example.bulletinBoard;

import com.example.bulletinBoard.domain.Category;
import com.example.bulletinBoard.domain.Member;
import com.example.bulletinBoard.domain.MemberRole;
import com.example.bulletinBoard.domain.Post;
import com.example.bulletinBoard.repository.CategoryRepository;
import com.example.bulletinBoard.repository.MemberRepository;
import com.example.bulletinBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initData(){
        log.info("============== test data init ==============");

        Member member = new Member("admin", "cjl2076@naver.com", "admin", MemberRole.ADMIN);
        Category category = new Category("공지", "공지 사항");
        memberRepository.save(member);
        categoryRepository.save(category);

        Post post1 = new Post("이용 가이드", "이용 가이드입니다.", member, category, 'N');
        Post post2 = new Post("공지 사항", "공지 사항입니다.", member, category, 'N');
        postRepository.save(post1);
        postRepository.save(post2);
    }
}