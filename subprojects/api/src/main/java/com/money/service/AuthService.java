package com.money.service;

import com.money.config.jwt.TokenProvider;
import com.money.domain.Member;
import com.money.domain.Platform;
import com.money.dto.response.MemberRegisterResponse;
import com.money.dto.response.SocialMemberResponse;
import com.money.dto.response.TokenResponse;
import com.money.exception.MemberAlreadyExistException;
import com.money.exception.NotFoundMemberException;
import com.money.exception.auth.PasswordMismatchException;
import com.money.repository.MemberRepository;
import java.time.LocalDate;
import java.util.Locale;
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
    private final SocialMemberProvider socialMemberProvider;

    public TokenResponse login(String email, String password) {
        Member findMember = memberRepository.findByEmailAndPlatform(email, Platform.EMAIL)
                .orElseThrow(NotFoundMemberException::new);
        validatePassword(findMember, password);

        String token = issueToken(findMember);
        return TokenResponse.of(findMember.getId(), token, true);
    }

    public TokenResponse socialLogin(String provider, String token) {
        Platform platform = Platform.fromString(provider);
        SocialMemberResponse platformMember = socialMemberProvider.getPlatformMember(platform, token);

        return generateOAuthTokenResponse(
                platform,
                platformMember.email(),
                platformMember.platformId()
        );
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
    public MemberRegisterResponse registerToOauth2(
            String provider, String platformId,
            String name, String phoneNumber, LocalDate birth
    ) {
        Platform platform = Platform.fromString(provider);
        Member registeredMember = memberRepository.findByPlatformAndPlatformId(platform, platformId)
                .orElseThrow(NotFoundMemberException::new);

        registeredMember.registerSocialMember(name, phoneNumber, birth);
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

    private TokenResponse generateOAuthTokenResponse(Platform platform, String email, String platformId) {
        return memberRepository.findByPlatformAndPlatformId(platform, platformId)
                .map(member -> {
                    String token = issueToken(member);
                    if (Boolean.TRUE.equals(member.isActivated())) {
                        return new TokenResponse(member.getId(), token, true);
                    }
                    return new TokenResponse(member.getId(), token, false);
                })
                .orElseGet(() -> {
                    Member oauthMember = Member.of(email, platform, platformId, Locale.KOREA);
                    Member savedMember = memberRepository.save(oauthMember);
                    String token = issueToken(savedMember);
                    return new TokenResponse(savedMember.getId(), token, false);
                });
    }
}
