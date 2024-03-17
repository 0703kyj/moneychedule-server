package com.money.service;

import com.money.config.jwt.TokenProvider;
import com.money.domain.Member;
import com.money.domain.Platform;
import com.money.dto.response.MemberRegisterResponse;
import com.money.dto.response.TokenResponse;
import com.money.exception.MemberAlreadyExistException;
import com.money.exception.NotFoundMemberException;
import com.money.exception.auth.PasswordMismatchException;
import com.money.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(String email, String password) {
        Member findMember = memberRepository.findByEmailAndPlatform(email, Platform.EMAIL)
                .orElseThrow(NotFoundMemberException::new);
        validatePassword(findMember, password);

        String token = issueToken(findMember);
        return TokenResponse.of(findMember.getId(), token);
    }

    @Transactional
    public MemberRegisterResponse registerToEmail(String email, String password) {
        Platform emailPlatform = Platform.EMAIL;

        if (Boolean.TRUE.equals(memberRepository.existsByEmailAndPlatform(email, emailPlatform))) {
            throw new MemberAlreadyExistException();
        }
        String encodedPassword = encodingPassword(password);

        Member registeredMember = Member.of(email, encodedPassword, emailPlatform);
        memberRepository.save(registeredMember);

        return MemberRegisterResponse.from(registeredMember);
    }

    @Transactional
    public MemberRegisterResponse registerToOauth2(String provider, String email,
            String platformId) {
        Platform platform = Platform.fromString(provider);
        if (Boolean.TRUE.equals(
                memberRepository.existsByPlatformAndPlatformId(platform, platformId))) {
            throw new MemberAlreadyExistException();
        }

        Member registeredMember = Member.of(email, platform, platformId);
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
