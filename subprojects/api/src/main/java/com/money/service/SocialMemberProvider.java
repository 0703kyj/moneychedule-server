package com.money.service;

import com.money.domain.Platform;
import com.money.dto.request.KakaoUserRequest;
import com.money.dto.response.KakaoUser;
import com.money.dto.response.SocialMemberResponse;
import com.money.exception.auth.InvalidPlatformException;
import com.money.exception.auth.InvalidTokenException;
import com.money.service.feign.KakaoUserClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialMemberProvider {
    private static final String AUTHORIZATION_BEARER = "Bearer ";
    private final KakaoUserClient kakaoUserClient;

    public SocialMemberResponse getPlatformMember(Platform platform, String token) {
        if(platform.equals(Platform.KAKAO)){
            return getKakaoMemberResponse(token);
        }
        if (platform.equals(Platform.GOOGLE)) {
            return getGoogleMemberResponse(token);
        }
        if (platform.equals(Platform.APPLE)) {
            return getAppleMemberResponse(token);
        }
        throw new InvalidPlatformException();
    }

    private SocialMemberResponse getAppleMemberResponse(String token) {
        return null;
    }

    private SocialMemberResponse getGoogleMemberResponse(String token) {
        return null;
    }

    private SocialMemberResponse getKakaoMemberResponse(String token) {
        try {
            KakaoUserRequest kakaoUserRequest = new KakaoUserRequest("[\"kakao_account.email\"]");
            KakaoUser user = kakaoUserClient.getUser(kakaoUserRequest, AUTHORIZATION_BEARER + token);
            return new SocialMemberResponse(String.valueOf(user.getId()), user.getEmail());
        } catch (FeignException e) {
            throw new InvalidTokenException();
        }
    }
}
