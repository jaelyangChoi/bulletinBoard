package com.example.bulletinBoard.domain.post;

import com.example.bulletinBoard.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

    @EntityGraph(attributePaths = {"author"})
    public List<Post> findAllByCategoryId(Long categoryId);

    public List<Post> findByAuthorId(Long authorId);
}
