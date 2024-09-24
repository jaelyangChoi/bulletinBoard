package com.example.bulletinBoard.service;

import com.example.bulletinBoard.controller.form.MemberForm;
import com.example.bulletinBoard.domain.Member;
import com.example.bulletinBoard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public Long join(MemberForm memberForm) {
        validateDuplicateMember(memberForm);
        Member member = memberForm.toEntity();
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 조회
     */
    @Transactional(readOnly = true)
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    private void validateDuplicateMember(MemberForm memberFrom) {
        List<Member> findMembers = memberRepository.findByEmail(memberFrom.getEmail());
        if(!findMembers.isEmpty())
            throw new IllegalStateException("해당 이메일로 가입된 회원이 존재합니다.");
    }
}
