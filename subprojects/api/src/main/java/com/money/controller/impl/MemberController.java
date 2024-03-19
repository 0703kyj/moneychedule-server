package com.money.controller.impl;

import com.money.controller.MemberApi;
import com.money.dto.request.member.MemberFollowRequest;
import com.money.dto.response.member.MemberFollowResponse;
import com.money.service.MemberService;
import com.money.service.member.MemberFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;
    private final MemberFollowService memberFollowService;

    @Override
    public String test() {
        return "OK";
    }

    @Override
    public MemberFollowResponse followMember(Long memberId, MemberFollowRequest request) {

        memberFollowService.followMember(memberId, request.followEmail());

        return null;
    }
}
