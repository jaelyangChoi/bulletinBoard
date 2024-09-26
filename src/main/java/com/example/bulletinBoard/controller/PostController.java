package com.example.bulletinBoard.controller;

import com.example.bulletinBoard.controller.form.PostForm;
import com.example.bulletinBoard.domain.Category;
import com.example.bulletinBoard.domain.Post;
import com.example.bulletinBoard.service.CategoryService;
import com.example.bulletinBoard.service.MemberService;
import com.example.bulletinBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final CategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public String getPosts(@PathVariable("categoryId") Long categoryId, Model model) {

        List<Post> posts = postService.findAll(categoryId);
        model.addAttribute("posts", posts);
        model.addAttribute("categoryId", categoryId);
        return "posts/list";
    }

    @GetMapping("/write/{categoryId}")
    public String write(@PathVariable("categoryId") Long categoryId, Model model) {
        PostForm postForm = new PostForm();
        postForm.setCategoryId(categoryId);
        postForm.setSecretYN('N');
        model.addAttribute("isEdit", false);
        model.addAttribute("postForm", postForm);
        return "posts/writeForm";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute PostForm form, RedirectAttributes redirectAttributes) {
        postService.createPost(form, memberService.findById(1L).orElseThrow());
        redirectAttributes.addAttribute("categoryId", form.getCategoryId());
        return "redirect:/posts/{categoryId}";
    }

    @GetMapping("/view/{postId}")
    public String view(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        model.addAttribute("post", post);
        return "posts/view";
    }

    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        model.addAttribute("postForm", postForm);
        model.addAttribute("postId", postId);
        model.addAttribute("isEdit", true);
        return "posts/writeForm";
    }

    @PutMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, @ModelAttribute PostForm form, RedirectAttributes redirectAttributes) {
        log.debug("edit : postId={} ", postId);
        //해당 사용자가 postId author인지 확인 로직 필요
        validateAuthor();
        postService.updatePost(postId, form);
        redirectAttributes.addAttribute("postId", postId);
        redirectAttributes.addAttribute("status", true); //쿼리파라미터 바인딩 후 남은 속성 값은 파라미터 형식으로 들어간다.
        return "redirect:/posts/view/{postId}";
    }

    private void validateAuthor() {

    }

}


