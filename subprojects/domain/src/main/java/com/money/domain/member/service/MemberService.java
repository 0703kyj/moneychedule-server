package com.money.domain.member.service;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.MemberAlreadyExistException;
import com.money.domain.member.repository.MemberRepository;
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
        Optional<Member> findMember = memberRepository.findByEmailAndPlatform(member.getEmail(),
                member.getPlatform());

        if (findMember.isPresent()) {
            throw new MemberAlreadyExistException();
        }
        return memberRepository.save(member);
    }

//    public Boolean validateInviteCode(String inviteCode) {
//        Member findMember = memberRepository.findByInvitedCode(inviteCode)
//                .orElseThrow(NotFoundMemberException::new);
//
//        Date now = new Date();
//        Date expiredTime = findMember.getInviteCode().getExpiredTime();
//
//        if (now.after(expiredTime)) {
//            throw new ExpiredCodeException();
//        }
//        return true;
//    }
}
