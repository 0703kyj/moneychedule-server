package com.money.controller;

import com.money.dto.response.auth.TokenResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dev")
public interface DevApi {

    @GetMapping("/token")
    ResponseEntity<TokenResponse> getToken(
            @Schema(description = "이메일", example = "0703kyj@naver.com")
            @RequestParam String email
    );

    @PostMapping("/deposit")
    void setDeposit(
            @Schema(description = "이메일", example = "0703kyj@naver.com")
            @RequestParam String email
    );

    @PostMapping("/withdraw")
    void setWithdraw(
            @Schema(description = "이메일", example = "0703kyj@naver.com")
            @RequestParam String email
    );
}
