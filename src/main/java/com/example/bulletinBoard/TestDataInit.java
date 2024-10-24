package com.example.bulletinBoard;

import com.example.bulletinBoard.domain.category.Category;
import com.example.bulletinBoard.domain.member.Member;
import com.example.bulletinBoard.domain.member.MemberRole;
import com.example.bulletinBoard.domain.post.Post;
import com.example.bulletinBoard.domain.category.CategoryRepository;
import com.example.bulletinBoard.domain.member.MemberRepository;
import com.example.bulletinBoard.domain.post.PostRepository;
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
    public void initData() {
        log.info("============== test data init ==============");

        Member adminUser = new Member("관리자", "admin@gmail.com", "admin", MemberRole.ADMIN);
        Category category1 = new Category("공지", "공지 사항");
        Category category2 = new Category("자유 게시판", "자유 게시판");
        memberRepository.save(adminUser);
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Post post1 = new Post("이용 가이드", "이용 가이드입니다.", adminUser, category1, 'N');
        Post post2 = new Post("공지 사항", "공지 사항입니다.", adminUser, category1, 'N');
        postRepository.save(post1);
        postRepository.save(post2);

        for (int i = 0; i < 100; i++) {
            postRepository.save(new Post("sample " + i, "sample", adminUser, category2, 'N'));
        }
    }
}
