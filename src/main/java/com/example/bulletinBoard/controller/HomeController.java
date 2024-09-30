package com.example.bulletinBoard.controller;

import com.example.bulletinBoard.domain.Category;
import com.example.bulletinBoard.domain.Member;
import com.example.bulletinBoard.domain.MemberRole;
import com.example.bulletinBoard.domain.Post;
import com.example.bulletinBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PostRepository postRepository;

    @GetMapping(value = {"/", "/login.do"})
    public String home(Model model) {
        log.info("home controller");
        Category category = new Category("name", "description");
        Post post = new Post("title", "content", new Member("name", "email@naver.com", "pass", MemberRole.ADMIN), category, 'Y');
        model.addAttribute("posts", List.of(post));
        model.addAttribute("categories", List.of(category));
        return "home";
    }


}