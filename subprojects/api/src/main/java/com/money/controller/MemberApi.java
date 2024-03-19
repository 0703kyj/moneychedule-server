package com.money.controller;

import com.money.config.auth.MemberId;
import com.money.dto.request.member.MemberFollowRequest;
import com.money.dto.response.member.MemberFollowResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @GetMapping("/test")
    String test();

    @PostMapping("/follow")
    MemberFollowResponse followMember(
            @MemberId Long memberId,
            @RequestBody MemberFollowRequest request
    );
}
