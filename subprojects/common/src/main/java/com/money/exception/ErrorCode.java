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
    INVALID_DATE("CM0004", "유효하지 않은 연 또는 월입니다."),
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
    INVALID_PLATFORM("AU0009", "Invalid Platform"),
    /**
     * Member Errors
     */
    NOT_FOUND_MEMBER("ME0001", "Member is not found"),
    ALREADY_EXIST_MEMBER("ME0002", "Member is already exist"),
    EXPIRED_CODE("ME0003", "연결 코드가 만료되었습니다."),
    OVER_FLOW_INVITE_CODE("ME0004", "연결 코드 개수가 없습니다.."),
    /**
     * Team Errors
     */
    NOT_FOUND_TEAM("TE0001", "해당 팀을 찾을 수 없습니다."),
    FAIL_GENERATE_CODE("TE0002", "초대 코드 생성에 실패했습니다. 다시 요청해 주세요."),
    OVER_FLOW_MEMBER_COUNT("TE0003", "초대 가능 인원 수가 초과되었습니다."),

    /**
     * Payment Error
     */
    NOT_FOUND_DEPOSIT_TYPE("PE0001", "입금 종류를 찾을 수 없습니다."),
    NOT_FOUND_WITHDRAW_TYPE("PE0002", "지출 종류를 찾을 수 없습니다."),
    INVALID_PAYMENT("PE0003", "입금 및 지출 금액을 0이하일 수 없습니다.");

    private final String code;
    private final String message;
}

