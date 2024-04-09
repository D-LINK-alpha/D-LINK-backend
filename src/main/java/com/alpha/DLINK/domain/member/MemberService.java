package com.alpha.DLINK.domain.member;


import com.alpha.DLINK.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    // 전체 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // 특정 회원 조회 (없으면 null)
    public Member findById(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        return byId.orElse(null);
    }

    // 회원 정보 수정
    public Long update(Long id, String name) {
        Optional<Member> findMember = memberRepository.findById(id);
        if(findMember.isPresent()) {
            Member member = findMember.get();
            member.setNickname(name);
        }
        return id;
    }

    // 회원 탈퇴
    public void delete(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        if(byId.isPresent()) {
            Member member = byId.get();
            memberRepository.delete(member);
        }
    }
}