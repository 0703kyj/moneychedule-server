package com.money.service;

import com.money.config.jwt.TokenProvider;
import com.money.domain.Member;
import com.money.dto.response.MemberRegisterResponse;
import com.money.dto.response.TokenResponse;
import com.money.exception.MemberAlreadyExistException;
import com.money.exception.NotFoundMemberException;
import com.money.exception.PasswordMismatchException;
import com.money.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(String email, String password) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
        validatePassword(findMember, password);

        String token = issueToken(findMember);
        return TokenResponse.of(findMember.getId(), token);
    }

    @Transactional
    public MemberRegisterResponse registerToEmail(String email, String password){
        if(Boolean.TRUE.equals(memberRepository.existsByEmail(email))){
            throw new MemberAlreadyExistException();
        }
        String encodedPassword = encodingPassword(password);

        Member registeredMember = Member.of(email, encodedPassword);
        memberRepository.save(registeredMember);

        return MemberRegisterResponse.from(registeredMember);
    }

    private void validatePassword(final Member findMember, final String password) {
        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new PasswordMismatchException();
        }
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String issueToken(final Member findMember) {
        return tokenProvider.createToken(findMember.getId());
    }
}
