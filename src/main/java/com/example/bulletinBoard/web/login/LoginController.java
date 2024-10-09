package com.example.bulletinBoard.web.login;

import com.example.bulletinBoard.domain.member.Member;
import com.example.bulletinBoard.domain.member.MemberRepository;
import com.example.bulletinBoard.domain.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RequestMapping("/login")
@Controller
public class LoginController {

    private final MemberService memberService;

    @GetMapping
    public String login(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletRequest request
            , @RequestParam(defaultValue = "/board") String redirectURL, Model model) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Optional<Member> loginMember = memberService.login(form.getEmail(), form.getPassword());

        if (loginMember.isEmpty()) {
            bindingResult.reject("loginFail", null);
            return "login/loginForm";
        }

        //로그인 성공 처리
        HttpSession session = request.getSession(); //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성

        //세션에 로그인 회원 정보 저장
        session.setAttribute("loginMember", loginMember.get());
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); //기존에 없으면 null 반환
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/board";
    }
}
