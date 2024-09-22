package com.example.bulletinBoard.domain;

import com.example.bulletinBoard.controller.CategoryForm;
import com.example.bulletinBoard.repository.CategoryRepository;
import com.example.bulletinBoard.service.CategoryService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryTest {

    @Autowired CategoryService categoryService;
    @Autowired CategoryRepository categoryRepository;
    @Autowired EntityManager em;

    @Test
    @Commit
    void 카테고리_생성(){
        //given
        CategoryForm form = new CategoryForm();
        form.setName("test");

        //when
        Long savedId = categoryService.createCategory(form);

        //then
        Category findCategory = categoryRepository.findById(savedId).orElseThrow();
        assertThat(form.getName()).isEqualTo(findCategory.getName());
    }

    @Test
    @Commit
    void 부모카테고리가_존재할경우_연관관계_생성(){
        //given
        Category parent = new Category("parent", "parent");
        categoryRepository.save(parent);

        CategoryForm form = new CategoryForm();
        form.setName("child");
        form.setParentId(parent.getId());

        //when
        Long savedId = categoryService.createCategory(form);

        em.flush();
        em.clear();

        //then
        Category findChild = categoryRepository.findById(savedId).orElseThrow();
        Category findParent = categoryRepository.findById(parent.getId()).orElseThrow();

        assertThat(findChild.getParent()).isEqualTo(findParent);
        assertThat(findParent.getChildren().getFirst()).isEqualTo(findChild);
    }
}