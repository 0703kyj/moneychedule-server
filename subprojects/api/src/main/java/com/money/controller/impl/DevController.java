package com.money.controller.impl;

import com.money.controller.DevApi;
import com.money.domain.member.entity.Member;
import com.money.domain.member.entity.enums.Platform;
import com.money.domain.member.exception.NotFoundMemberException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.payment.service.PaymentService;
import com.money.dto.response.auth.TokenResponse;
import com.money.service.auth.AuthService;
import com.money.system.config.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DevController implements DevApi {
    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final PaymentService paymentService;

    @Override
    public ResponseEntity<TokenResponse> getToken(String email) {
        Boolean isRegistered = memberRepository.existsByEmailAndPlatform(email, Platform.EMAIL);

        if(Boolean.FALSE.equals(isRegistered)) {
            authService.registerToEmail(email, "123");
        }
        TokenResponse response = authService.login(email, "123");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + response.token());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(response);
    }

    @Override
    public void setDeposit(String email) {
        Member member = memberRepository.findByEmailAndPlatform(email, Platform.EMAIL)
                .orElseThrow(NotFoundMemberException::new);

        for (int i = 0; i < 100; i++) {
            paymentService.saveDeposit(member.getId(),"1", (long) i,"월급");
        }
    }

    @Override
    public void setWithdraw(String email) {
        Member member = memberRepository.findByEmailAndPlatform(email, Platform.EMAIL)
                .orElseThrow(NotFoundMemberException::new);

        for (int i = 0; i < 100; i++) {
            paymentService.saveWithdraw(member.getId(),"1", (long) i,"식비");
        }
    }
}
