package com.money.service;

import com.money.domain.Member;
import com.money.exception.ExpiredCodeException;
import com.money.exception.MemberAlreadyExistException;
import com.money.exception.NotFoundMemberException;
import com.money.repository.MemberRepository;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmailAndPlatform(member.getEmail(), member.getPlatform());

        if (findMember.isPresent()) {
            throw new MemberAlreadyExistException();
        }
        return memberRepository.save(member);
    }

    public String validateInvitedCode(String invitedCode){
        Member findMember = memberRepository.findByInvitedCode(invitedCode)
                .orElseThrow(NotFoundMemberException::new);

        Date now = new Date();
        Date expiredTime = findMember.getInvitedCode().getExpiredTime();

        if (now.after(expiredTime)) {
            throw new ExpiredCodeException();
        }
        return invitedCode;
    }
}
