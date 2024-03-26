package com.money.controller.impl;

import com.money.system.config.jwt.JwtFilter;
import com.money.controller.AuthApi;
import com.money.dto.request.auth.EmailRequest;
import com.money.dto.request.auth.SocialLoginRequest;
import com.money.dto.request.auth.SocialRegisterRequest;
import com.money.dto.response.member.MemberResponse;
import com.money.dto.response.auth.TokenResponse;
import com.money.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<TokenResponse> login(EmailRequest request) {
        TokenResponse response = authService.login(request.email(),request.password());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + response.token());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(response);
    }

    @Override
    public ResponseEntity<TokenResponse> socialLogin(String provider, SocialLoginRequest request) {
        TokenResponse response = authService.socialLogin(provider, request.accessToken());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + response.token());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(response);
    }

    @Override
    public ResponseEntity<MemberResponse> registerToEmail(EmailRequest request) {
        MemberResponse response = authService.registerToEmail(request.email(),
                request.password());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MemberResponse> register(String provider, SocialRegisterRequest request) {
        MemberResponse response = authService.registerToOauth(
                provider, request.platformId(), request.name(), request.phoneNumber(), request.birth());

        return ResponseEntity.ok(response);
    }
}
