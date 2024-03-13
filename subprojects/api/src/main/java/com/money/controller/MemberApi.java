package com.money.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "회원 API", description = "회원 관련 API")
@RequestMapping("/api/v1/members")
public interface MemberApi {
    @GetMapping("/test")
    public String test();
}
