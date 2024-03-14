package com.money.controller.impl;

import com.money.config.jwt.JwtFilter;
import com.money.controller.AuthApi;
import com.money.dto.request.EmailRequest;
import com.money.dto.response.MemberRegisterResponse;
import com.money.dto.response.TokenResponse;
import com.money.service.AuthService;
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
    public void oauth2Login(String provider) {

    }

    @Override
    public ResponseEntity<MemberRegisterResponse> registerToEmail(EmailRequest request) {
        MemberRegisterResponse response = authService.registerToEmail(request.email(),
                request.password());

        return ResponseEntity.ok(response);
    }
}
