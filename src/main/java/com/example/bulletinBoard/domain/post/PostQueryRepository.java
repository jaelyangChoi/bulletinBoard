package com.example.bulletinBoard.domain.post;

import java.util.List;

public interface PostQueryRepository {
    public List<Post> findBySearchCond(PostSearch cond);
}
