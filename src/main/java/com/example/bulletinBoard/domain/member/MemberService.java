package com.example.bulletinBoard.domain.member;

import com.example.bulletinBoard.web.member.MemberForm;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
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


    @Transactional(readOnly = true)
    public Optional<Member> login(String email, String password) {
        Member findMember;
        try {
            findMember = memberRepository.findByEmail(email);
        } catch (NoResultException e) {
            return Optional.empty();
        }

        if (!findMember.getPassword().equals(password)) {
            log.info("Invalid password");
            return Optional.empty();
        }
        return Optional.of(findMember);
    }

    private void validateDuplicateMember(MemberForm memberFrom) {
        Member findMember = memberRepository.findByEmail(memberFrom.getEmail());
        if (findMember != null)
            throw new IllegalStateException("해당 이메일로 가입된 회원이 존재합니다.");
    }
}
