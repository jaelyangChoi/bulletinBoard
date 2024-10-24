package com.example.bulletinBoard.service;

import com.example.bulletinBoard.domain.category.Category;
import com.example.bulletinBoard.domain.member.Member;
import com.example.bulletinBoard.domain.member.MemberRole;
import com.example.bulletinBoard.domain.post.Post;
import com.example.bulletinBoard.domain.post.PostRepository;
import com.example.bulletinBoard.domain.post.PostSearch;
import com.example.bulletinBoard.domain.post.PostService;
import com.example.bulletinBoard.web.post.PostForm;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired EntityManager em;

    @Test
    void 게시글작성_유효성검사() {
        //given
        PostForm form = new PostForm();
        form.setTitle("test");
        Category category = new Category("title", "desc");
        Member member = new Member("name", "email", "pass", MemberRole.ADMIN);
        em.persist(category);
        em.persist(member);

        //then : 카테고리, 멤버가 등록되지 않으면 예외 발생
        assertThatThrownBy(()->postService.createPost(form, member))
                .isInstanceOf(IllegalStateException.class);

        //then : 등록 후 정상 처리
        form.setCategoryId(category.getId());

        Long savedId = postService.createPost(form, member);
        Post findPost = postRepository.findById(savedId).orElseThrow();

        assertThat(savedId).isEqualTo(findPost.getId());
    }

    @Test
    void 검색조건_조회(){
        //given
        Member member1 = new Member("test1", "email1", "pass1", MemberRole.ADMIN);
        Member member2 = new Member("test2", "email2", "pass2", MemberRole.USER);

        em.persist(member1);
        em.persist(member2);

        Post post1 = new Post("title1", "content", member1, null, 'Y');
        Post post2 = new Post("title2", "content", member1, null, 'Y');
        Post post3 = new Post("title3", "content", member2, null, 'Y');
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        //when
        List<Post> byMember = postService.findByMember(member1.getId());
        PostSearch postSearch = new PostSearch();
        postSearch.setTitle("itl");
        List<Post> bySearchCond = postService.findByCondition(postSearch);

        //then
        assertThat(byMember.size()).isEqualTo(2);
        assertThat(bySearchCond.size()).isEqualTo(3);

    }
}