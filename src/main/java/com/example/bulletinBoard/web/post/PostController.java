package com.example.bulletinBoard.web.post;

import com.example.bulletinBoard.domain.category.Category;
import com.example.bulletinBoard.domain.member.Member;
import com.example.bulletinBoard.domain.post.Post;
import com.example.bulletinBoard.domain.category.CategoryService;
import com.example.bulletinBoard.domain.member.MemberService;
import com.example.bulletinBoard.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
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

    //검증기 적용. 컨트롤러 호출시마다 호출됨. binder는 매번 생성됨
    @InitBinder("postForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new PostFormValidator());
    }

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
        model.addAttribute("postForm", postForm);
        return "board/writeForm";
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/write")
    public String write(@Validated @ModelAttribute("postForm") PostForm postForm, BindingResult bindingResult
            ,RedirectAttributes redirectAttributes) {
        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            return "board/writeForm";
        }

        Long postId = postService.createPost(postForm, memberService.findById(1L).orElseThrow());

        redirectAttributes.addAttribute("postId", postId);
        redirectAttributes.addAttribute("status", true); //pathVariable 이 없으면 쿼리 파라미터로 처리
        return "redirect:/board/view/{postId}";
    }

    /**
     * 게시글 수정 폼
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, Model model) {
        isValidAuthor(null);
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
    public String edit(@PathVariable("postId") Long postId, @Validated @ModelAttribute("postForm") PostForm postForm, BindingResult bindingResult,
                       Model model, RedirectAttributes redirectAttributes) {
        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("edit fail!!!!!!!! errors={}", bindingResult.getFieldError());
            model.addAttribute("isEdit", true);
            return "board/writeForm";
        }

        isValidAuthor(null);
        postService.updatePost(postId, postForm);

        redirectAttributes.addAttribute("postId", postId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/board/view/{postId}";
    }

    /**
     * 게시글 삭제
     */
    @GetMapping("/delete/{categoryId}/{postId}")
    public String delete(@PathVariable("categoryId") String categoryId, @PathVariable("postId") Long postId, RedirectAttributes redirectAttributes) {
        isValidAuthor(null);
        postService.deletePost(postId);

        redirectAttributes.addAttribute("categoryId", categoryId);
        return "redirect:/board";
    }


    /**
     * 해당 사용자가 postId author인지 확인
     */
    private void isValidAuthor(Member member) {

    }
}

