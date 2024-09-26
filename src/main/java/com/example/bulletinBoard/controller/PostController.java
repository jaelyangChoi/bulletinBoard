package com.example.bulletinBoard.controller;

import com.example.bulletinBoard.controller.form.PostForm;
import com.example.bulletinBoard.domain.Category;
import com.example.bulletinBoard.domain.Post;
import com.example.bulletinBoard.service.CategoryService;
import com.example.bulletinBoard.service.MemberService;
import com.example.bulletinBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final CategoryService categoryService;

    @GetMapping
    public String getPosts(Model model) {
        List<Post> posts = postService.findAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("categories", categories);
        return "posts/list";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("title", "새 글 작성");
        PostForm postForm = new PostForm();
        postForm.setCategoryId(1L);
        postForm.setSecretYN('N');
        model.addAttribute("postForm", postForm);
        return "posts/writeForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute PostForm form) {
        postService.createPost(form, memberService.findById(1L).orElseThrow());
        return "redirect:/posts";
    }

    @GetMapping("/view/{postId}")
    public String view(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        model.addAttribute("post", post);
        return "posts/view";
    }
}
