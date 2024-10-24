package com.example.bulletinBoard.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

    @EntityGraph(attributePaths = {"author"})
    List<Post> findAllByCategoryId(Long categoryId);

    List<Post> findByAuthorId(Long authorId);

    Page<Post> findAllByCategoryId(Long categoryId, Pageable pageable);
}
