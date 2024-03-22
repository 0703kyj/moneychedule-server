package com.money.service.auth;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.MemberAlreadyExistException;
import com.money.domain.member.exception.NotFoundMemberException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.member.service.MemberService;
import com.money.domain.member.entity.enums.Platform;
import com.money.system.config.jwt.TokenProvider;
import com.money.dto.response.auth.MemberRegisterResponse;
import com.money.dto.response.auth.SocialMemberResponse;
import com.money.dto.response.auth.TokenResponse;
import com.money.system.exception.PasswordMismatchException;
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
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final SocialMemberProvider socialMemberProvider;

    public TokenResponse login(String email, String password) {
        Member findMember = memberRepository.findByEmailAndPlatform(email, Platform.EMAIL)
                .orElseThrow(NotFoundMemberException::new);
        validatePassword(findMember, password);

        String token = issueToken(findMember);
        return TokenResponse.of(findMember.getId(), token, true);
    }

    public TokenResponse socialLogin(String provider, String accessToken) {
        Platform platform = Platform.fromString(provider);
        SocialMemberResponse platformMember = socialMemberProvider.getPlatformMember(platform, accessToken);

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
    public MemberRegisterResponse registerToOauth(
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
                    Member savedMember = memberService.saveMember(oauthMember);
                    String token = issueToken(savedMember);
                    return new TokenResponse(savedMember.getId(), token, false);
                });
    }
}
