package com.example.bulletinBoard.domain.category;

import com.example.bulletinBoard.web.category.CategoryForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
ADMIN 사용자는 카테고리를 관리할 수 있다.
카테고리를 생성할 때 부모가 있는 경우 추가

*/
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryJpaRepository categoryRepository;

    public Long createCategory(CategoryForm form) {
        Category category = form.toEntity();

        //부모가 존재하면 부모,자식 연관관계를 만든다.
        if(form.getParentId() != null) {
            categoryRepository.findById(form.getParentId())
                    .ifPresent(p -> p.addChildCategory(category)); //dirty check
        }

        categoryRepository.save(category);
        return category.getId();
    }

    public Category findOne(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("등록되지 않은 카테고리 ID 입니다."));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
