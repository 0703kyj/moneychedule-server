package com.money.controller;

import com.money.dto.request.EmailRequest;
import com.money.dto.request.Oauth2Request;
import com.money.dto.response.ErrorResponse;
import com.money.dto.response.MemberRegisterResponse;
import com.money.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API", description = "인증 관련 API")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class))),
})
@RestController
@RequestMapping("/api/v1/auth")
public interface AuthApi {

    @Operation(summary = "내부 로그인", description = "테스트용 로그인을 진행합니다.")
    @PostMapping
    ResponseEntity<TokenResponse> login(
            @RequestBody @Valid EmailRequest request
    );

    @Operation(summary = "네이티브 소셜 로그인", description = "네이티브(apple 등) 소셜 로그인을 진행합니다.")
    @PostMapping("/login/{provider}")
    void oauth2Login(
            @PathVariable("provider") @Parameter(example = "GOOGLE", description = "oAuth 제공자 이름")  String provider,
            @RequestBody @Valid Oauth2Request request
    );

    @Operation(summary = "이메일 회원가입", description = "이메일 회원가입을 진행합니다.")
    @PostMapping(value = "/register/email")
    ResponseEntity<MemberRegisterResponse> registerToEmail(
            @RequestBody @Valid EmailRequest request
    );

    @Operation(summary = "소셜 회원가입", description = "소셜 회원가입을 진행합니다.")
    @PostMapping(value = "/register/{provider}")
    ResponseEntity<MemberRegisterResponse> register(
            @PathVariable("provider") @Parameter(example = "GOOGLE", description = "oAuth 제공자 이름")  String provider,
            @RequestBody @Valid Oauth2Request request
    );
}


