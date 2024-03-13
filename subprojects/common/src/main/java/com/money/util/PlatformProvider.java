package com.money.util;

import java.security.InvalidParameterException;

public enum PlatformProvider {
    APPLE, //애플 로그인
    KAKAO, //카카오 로그인
    GOOGLE, //구글 로그인
    INTERNAL; //내부 로그인

    public static PlatformProvider fromString(String provider) {
        return switch (provider.toUpperCase()) {
            case "APPLE" -> APPLE;
            case "KAKAO" -> KAKAO;
            case "GOOGLE" -> GOOGLE;
            case "INTERNAL" -> INTERNAL;
            default -> throw new InvalidParameterException();
        };
    }
}
