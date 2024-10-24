package com.example.bulletinBoard.domain.post;

import com.example.bulletinBoard.domain.category.CategoryRepository;
import com.example.bulletinBoard.web.post.PostForm;
import com.example.bulletinBoard.domain.category.Category;
import com.example.bulletinBoard.domain.category.CategoryJpaRepository;
import com.example.bulletinBoard.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long createPost(PostForm form, Member member) {
        //유효성 검사
        Category category = baseValidation(form);

        //등록
        Post post = form.toEntity(member, category);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Post updatePost(Long id, PostForm form) {
        //유효성 검사
        Category category = baseValidation(form);

        //업데이트
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 게시글 입니다."));
        PostUpdateDto dto = new PostUpdateDto(form.getTitle(), form.getContent(), form.getSecretYN());
        post.update(dto);

        return post;
    }

    @Transactional
    public void deletePost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 게시글 입니다."));
        postRepository.delete(findPost);
    }

    public List<Post> findAll(Long categoryId) {
        return postRepository.findAllByCategoryId(categoryId);
    }

    public List<Post> findByCondition(PostSearch postSearch) {
        return postRepository.findBySearchCond(postSearch);
    }

    public List<Post> findByMember(Long memberId) {
        return postRepository.findByAuthorId(memberId);
    }

    private Category baseValidation(PostForm form) {
        //카테고리 검사
        if (form.getCategoryId() == null)
            throw new IllegalStateException("카테고리 ID 누락");
        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 카테고리 ID 입니다."));

        //제목 유효성 검사
        validateTitle(form.getTitle());

        return category;
    }

    private void validateTitle(String title) {
        //유효성 검사에 문제가 있을 시 ex throw
    }

    public Post findOne(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 게시글 ID 입니다."));
    }
}
