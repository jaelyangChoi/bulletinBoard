package com.example.bulletinBoard.web;

import com.example.bulletinBoard.domain.member.Member;
import com.example.bulletinBoard.web.login.LoginForm;
import com.example.bulletinBoard.web.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {


    @GetMapping(value = {"/", "/login.do"})
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        //세션에 회원이 없으면 home 으로 이동
        if (loginMember == null) {
            model.addAttribute("loginForm", new LoginForm());
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "redirect:/board";
    }

}