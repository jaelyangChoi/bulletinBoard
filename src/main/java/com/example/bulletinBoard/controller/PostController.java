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
@RequestMapping("/board")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final CategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    /**
     * 게시글 목록 보기
     */
    @GetMapping("")
    public String getPosts(@RequestParam(required = false, defaultValue = "1") Long categoryId, Model model) {
        model.addAttribute("posts", postService.findAll(categoryId));
        model.addAttribute("category", categoryService.findOne(categoryId));
        return "board/list";
    }

    /**
     * 게시글 상세 보기
     */
    @GetMapping("/view/{postId}")
    public String view(@PathVariable("postId") Long postId, Model model) {
        model.addAttribute("post", postService.findOne(postId));
        return "board/view";
    }

    /**
     * 게시글 작성 폼
     */
    @GetMapping("/write/{categoryId}")
    public String write(@PathVariable("categoryId") Long categoryId, Model model) {
        PostForm postForm = new PostForm();
        postForm.setCategoryId(categoryId);
        postForm.setSecretYN('N');
        model.addAttribute("isEdit", false);
        model.addAttribute("postForm", postForm);
        return "board/writeForm";
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/write")
    public String write(@ModelAttribute PostForm form, RedirectAttributes redirectAttributes) {
        Long postId = postService.createPost(form, memberService.findById(1L).orElseThrow());
        //pathVarible이 없으면 쿼리 파라미터로 처리
        redirectAttributes.addAttribute("postId", postId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/board/view/{postId}";
    }

    /**
     * 게시글 수정 폼
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findOne(postId);
        model.addAttribute("postForm", PostForm.fromEntity(post));
        model.addAttribute("postId", postId);
        model.addAttribute("isEdit", true);
        return "board/writeForm";
    }

    /**
     * 게시글 수정
     */
    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, @ModelAttribute PostForm form, RedirectAttributes redirectAttributes) {
        //
        validateAuthor();
        postService.updatePost(postId, form);
        redirectAttributes.addAttribute("postId", postId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/board/view/{postId}";
    }

    /**
     * 게시글 삭제
     */
    @GetMapping("/delete/{categoryId}/{postId}")
    public String delete(@PathVariable("categoryId") String categoryId, @PathVariable("postId") Long postId, RedirectAttributes redirectAttributes) {
        validateAuthor();
        postService.deletePost(postId);

        redirectAttributes.addAttribute("categoryId", categoryId);
        return "redirect:/board";
    }

    /**
     * 해당 사용자가 postId author인지 확인
     */
    private void validateAuthor() {

    }

}


