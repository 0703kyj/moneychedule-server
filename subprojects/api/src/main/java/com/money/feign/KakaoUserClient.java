package com.money.feign;

import com.money.dto.request.auth.KakaoUserRequest;
import com.money.dto.response.auth.KakaoUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-user-client", url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoUserClient {

    @GetMapping
    KakaoUser getUser(@SpringQueryMap KakaoUserRequest request, @RequestHeader(name = "Authorization") String token);
}
