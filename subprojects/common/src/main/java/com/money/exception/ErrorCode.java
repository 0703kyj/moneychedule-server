package com.money.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * Common Errors
     */
    UNKNOWN_SERVER_ERROR("CM0001", "Unknown Server Error"),
    INVALID_INPUT_VALUE("CM0002", "Invalid Input Value"),
    METHOD_NOT_ALLOWED("CM0003", "Method Not Allowed"),
    /**
     * Auth Related Errors
     */
    AUTHENTICATION_FAILED("AU0001", "Authentication failed"),
    TOKEN_AUTHENTICATION_FAILED("AU0002", "Token Authentication failed"),
    AUTHORIZATION_FAILED("AU0003", "No Permission"),
    REFRESH_TOKEN_INVALID("AU0004", "Refresh Token is invalid"),
    TOKEN_EXPIRED("AU0005", "Token is expired"),
    INVALID_TOKEN("AU0006","Invalid Token"),
    BLANK_TOKEN("AU0007", "Blank Token"),
    MISMATCH_PASSWORD("AU0008", "Mismatch Password"),
    /**
     * Member Errors
     */
    NOT_FOUND_MEMBER("ME0001", "Member is not found");

    private final String code;
    private final String message;
}

