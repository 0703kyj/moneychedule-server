package com.money.service;

import com.money.config.jwt.TokenProvider;
import com.money.domain.Member;
import com.money.dto.request.EmailRequest;
import com.money.dto.response.TokenResponse;
import com.money.exception.NotFoundMemberException;
import com.money.exception.auth.PasswordMismatchException;
import com.money.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public TokenResponse login(EmailRequest request) {
        Member findMember = memberRepository.findByEmail(request.email())
                .orElseThrow(NotFoundMemberException::new);
        validatePassword(findMember, request.password());

        String token = issueToken(findMember);
        return TokenResponse.of(findMember.getId(), token);
    }

    private void validatePassword(final Member findMember, final String password) {
        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new PasswordMismatchException();
        }
    }

    private String issueToken(final Member findMember) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(findMember.getEmail(), findMember.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.createToken(authentication);
    }
}
