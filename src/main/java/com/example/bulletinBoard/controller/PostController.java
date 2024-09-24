package com.example.bulletinBoard.controller;

import com.example.bulletinBoard.controller.form.PostForm;
import com.example.bulletinBoard.domain.Post;
import com.example.bulletinBoard.service.MemberService;
import com.example.bulletinBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String getPosts(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "board/list";
    }

    @GetMapping("/post")
    public String post(Model model) {
        model.addAttribute("title", "새 글 작성");
        PostForm postForm = new PostForm();
        postForm.setCategoryId(1L);
        postForm.setSecretYN('N');
        model.addAttribute("postForm", postForm);
        return "board/writeForm";
    }

    @PostMapping("/post")
    public String post(@ModelAttribute PostForm form) {
        postService.createPost(form, memberService.findById(1L).orElseThrow());
        return "redirect:/board/list";
    }

    @GetMapping("/view/{postId}")
    public String view(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        model.addAttribute("post", post);
        return "board/view";
    }
}
