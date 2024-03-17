package com.money.domain;

import java.security.InvalidParameterException;
import lombok.EqualsAndHashCode;

public enum Platform {
    KAKAO,GOOGLE,APPLE,EMAIL;

    public static Platform fromString(String provider) {
        return switch (provider.toUpperCase()) {
            case "KAKAO" -> KAKAO;
            case "GOOGLE" -> GOOGLE;
            case "APPLE" -> APPLE;
            case "EMAIL" -> EMAIL;
            default -> throw new InvalidParameterException();
        };
    }
}
