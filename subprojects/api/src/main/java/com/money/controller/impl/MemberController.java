package com.money.controller.impl;

import com.money.controller.MemberApi;
import com.money.domain.member.entity.Member;
import com.money.domain.member.service.MemberService;
import com.money.dto.request.member.SetTeamRequest;
import com.money.dto.response.member.InviteCodeResponse;
import com.money.dto.response.member.MemberIdListResponse;
import com.money.dto.response.member.SetTeamResponse;
import com.money.service.member.MemberInviteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberInviteService memberInviteService;
    private final MemberService memberService;

    @Override
    public ResponseEntity<MemberIdListResponse> getMembers(Long memberId) {

        List<Member> members = memberService.getMembers(memberId);

        MemberIdListResponse response = MemberIdListResponse.from(members);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InviteCodeResponse> getInviteCode(Long memberId) {
        InviteCodeResponse response = memberInviteService.getInviteCode(memberId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SetTeamResponse> setTeam(Long memberId, SetTeamRequest request) {
        SetTeamResponse response = memberInviteService.setTeam(memberId, request.inviteCode());
        return ResponseEntity.ok(response);
    }
}
