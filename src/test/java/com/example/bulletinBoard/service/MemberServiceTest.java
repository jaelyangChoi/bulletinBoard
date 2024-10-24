package com.example.bulletinBoard.service;

import com.example.bulletinBoard.domain.member.Member;
import com.example.bulletinBoard.domain.member.MemberRepository;
import com.example.bulletinBoard.domain.member.MemberService;
import com.example.bulletinBoard.web.member.MemberForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given
        MemberForm memberForm = new MemberForm();
        memberForm.setName("member1");

        //when
        Long savedId = memberService.join(memberForm);

        //then
        Member findMember = memberRepository.findById(savedId).orElseThrow();
        assertThat(memberForm.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        MemberForm form1 = new MemberForm();
        MemberForm form2 = new MemberForm();
        form1.setEmail("test@test.com");
        form2.setEmail("test@test.com");

        //when
        memberService.join(form1);

        //then
        assertThatThrownBy(()->memberService.join(form2))
                .isInstanceOf(IllegalStateException.class);
    }
}