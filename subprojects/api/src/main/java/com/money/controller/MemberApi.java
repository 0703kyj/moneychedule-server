package com.money.controller;

import com.money.config.auth.MemberId;
import com.money.dto.request.member.SetTeamRequest;
import com.money.dto.response.member.InviteCodeResponse;
import com.money.dto.response.member.MemberIdListResponse;
import com.money.dto.response.member.SetTeamResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "회원 API", description = "회원 관련 API")
@RequestMapping("/api/v1/members")
@SecurityRequirement(name = "JWT")
public interface MemberApi {

    @GetMapping
    ResponseEntity<MemberIdListResponse> getMembers(
            @MemberId Long memberId
    );

    @GetMapping("/invite-code")
    ResponseEntity<InviteCodeResponse> getInviteCode(
            @MemberId Long memberId
    );

    @PostMapping("/invite-code")
    ResponseEntity<SetTeamResponse> setTeam(
            @MemberId Long memberId,
            @RequestBody SetTeamRequest request
    );
}
