package com.money.controller;

import com.money.dto.response.auth.TokenResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "개발 API", description = "개발 임시 관련 API")
@RequestMapping("/api/v1/dev")
public interface DevApi {

    @GetMapping("/token")
    ResponseEntity<TokenResponse> getToken();

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
