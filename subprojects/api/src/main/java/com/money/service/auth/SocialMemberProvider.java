package com.money.service.auth;

import com.money.util.Platform;
import com.money.dto.request.auth.KakaoUserRequest;
import com.money.dto.response.auth.KakaoUser;
import com.money.dto.response.auth.SocialMemberResponse;
import com.money.system.exception.InvalidPlatformException;
import com.money.system.exception.InvalidTokenException;
import com.money.feign.KakaoUserClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialMemberProvider {
    private static final String AUTHORIZATION_BEARER = "Bearer ";
    private final KakaoUserClient kakaoUserClient;

    public SocialMemberResponse getPlatformMember(Platform platform, String accessToken) {
        if(platform.equals(Platform.KAKAO)){
            return getKakaoMemberResponse(accessToken);
        }
        if (platform.equals(Platform.GOOGLE)) {
            return getGoogleMemberResponse(accessToken);
        }
        if (platform.equals(Platform.APPLE)) {
            return getAppleMemberResponse(accessToken);
        }
        throw new InvalidPlatformException();
    }

    private SocialMemberResponse getAppleMemberResponse(String accessToken) {
        return null;
    }

    private SocialMemberResponse getGoogleMemberResponse(String accessToken) {
        return null;
    }

    private SocialMemberResponse getKakaoMemberResponse(String accessToken) {
        try {
            KakaoUserRequest kakaoUserRequest = new KakaoUserRequest("[\"kakao_account.email\"]");
            KakaoUser user = kakaoUserClient.getUser(kakaoUserRequest, AUTHORIZATION_BEARER + accessToken);

            return SocialMemberResponse.builder()
                    .email(user.getEmail())
                    .platformId(String.valueOf(user.getId()))
                    .build();
        } catch (FeignException e) {
            throw new InvalidTokenException();
        }
    }
}
