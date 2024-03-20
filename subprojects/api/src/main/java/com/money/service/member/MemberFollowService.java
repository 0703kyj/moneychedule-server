package com.money.service.member;

import com.money.domain.member.entity.InviteCode;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.member.service.MemberService;
import com.money.dto.response.member.MemberFollowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberFollowService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Transactional
    public InviteCode getInviteCode(){
        return InviteCodeProvider.getInviteCode();
    }

    @Transactional
    public MemberFollowResponse followMember(Long memberId, String followEmail) {


        return null;
    }
}
