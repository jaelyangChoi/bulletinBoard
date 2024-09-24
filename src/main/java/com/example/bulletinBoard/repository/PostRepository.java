package com.example.bulletinBoard.repository;

import com.example.bulletinBoard.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p join fetch p.author", Post.class)
                .getResultList();
    }

    public List<Post> findBySearchCond(PostSearch cond) {
        String jpql = "select p from Post p";
        boolean isFirstCondition = true;

        //제목 검색
        if (StringUtils.hasText(cond.getTitle())) {
            jpql += " where p.title like :title";
            isFirstCondition = false;
        }
        // 날짜 등 생략. Qerydsl로 update 예정
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        if (StringUtils.hasText(cond.getTitle())) {
            query.setParameter("title", "%" + cond.getTitle() + "%");
        }
        return query.getResultList();
    }

    public List<Post> findByAuthor(Long authorId) {
        return em.createQuery("select p from Post p where p.author.id = :authorId", Post.class)
                .setParameter("authorId", authorId)
                .getResultList();
    }
}
