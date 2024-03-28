package com.money.controller.impl;

import com.money.controller.DevApi;
import com.money.domain.member.entity.Member;
import com.money.domain.member.entity.enums.Platform;
import com.money.domain.member.exception.NotFoundMemberException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.payment.entity.enums.PaymentType;
import com.money.domain.payment.service.PaymentService;
import com.money.domain.team.entity.Team;
import com.money.domain.team.service.TeamService;
import com.money.dto.response.auth.TokenResponse;
import com.money.service.auth.AuthService;
import com.money.system.config.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class DevController implements DevApi {

    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final TeamService teamService;
    private final PaymentService paymentService;

    @Override
    public ResponseEntity<TokenResponse> getToken(String email) {
        Boolean isRegistered = memberRepository.existsByEmailAndPlatform(email, Platform.EMAIL);

        if (Boolean.FALSE.equals(isRegistered)) {
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

        for (int i = 1; i < 100; i++) {
            paymentService.savePayment(member.getId(), "1", (long) i, PaymentType.DEPOSIT, "월급");
        }
    }

    @Override
    public void setWithdraw(String email) {
        Member member = memberRepository.findByEmailAndPlatform(email, Platform.EMAIL)
                .orElseThrow(NotFoundMemberException::new);

        for (int i = 1; i < 100; i++) {
            paymentService.savePayment(member.getId(), "1", (long) i, PaymentType.WITHDRAW, "식비");
        }
    }

    @Override
    @Transactional
    public void setTeam(Long member1Id, Long member2Id) {
        Member member1 = memberRepository.findById(member1Id)
                .orElseThrow(NotFoundMemberException::new);
        Member member2 = memberRepository.findById(member2Id)
                .orElseThrow(NotFoundMemberException::new);

        Team newTeam = teamService.createNewTeam();
        member1.updateTeam(newTeam);
        member2.updateTeam(newTeam);
    }
}
