package com.money.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * Common Errors
     */
    UNKNOWN_SERVER_ERROR("CM0001", "일시적으로 접속이 원활하지 않습니다. 서버 팀에 문의 부탁드립니다."),
    INVALID_INPUT_VALUE("CM0002", "유효하지 않은 입력입니다."),
    METHOD_NOT_ALLOWED("CM0003", "허가되지 않은 메서드입니다."),
    INVALID_DATE("CM0004", "유효하지 않은 연 또는 월입니다."),
    /**
     * Auth Related Errors
     */
    AUTHENTICATION_FAILED("AU0001", "인증에 실패하였습니다."),
    TOKEN_AUTHENTICATION_FAILED("AU0002", "토큰 인증에 실패하였습니다."),
    AUTHORIZATION_FAILED("AU0003", "접근 권한이 없습니다."),
    REFRESH_TOKEN_INVALID("AU0004", "유효하지 않은 리프레시 토큰입니다."),
    TOKEN_EXPIRED("AU0005", "유효하지 않은 토큰입니다."),
    BLANK_TOKEN("AU0007", "토큰이 비어있습니다."),
    MISMATCH_PASSWORD("AU0008", "비밀번호가 맞지 않습니다."),
    INVALID_PLATFORM("AU0009", "유효하지 않은 플랫폼입니다."),
    /**
     * Member Errors
     */
    NOT_FOUND_MEMBER("ME0001", "해당 사용자를 찾을 수 없습니다."),
    ALREADY_EXIST_MEMBER("ME0002", "이미 존재하는 사용자입니다."),
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
    INVALID_PAYMENT("PE0003", "입금 및 지출 금액을 0이하일 수 없습니다."),

    /**
     * Schedule Error
     */
    NOT_FOUND_REPEAT_TYPE("SC0001", "반복 종류를 찾을 수 없습니다."),
    NOT_FOUND_LABEL("SC0002", "라벨을 찾을 수 없습니다."),
    NOT_FOUND_SCHEDULE("SC0003", "일정을 찾을 수 없습니다."),
    NOT_FOUND_ATTENDEE("SC0004", "참가자를 찾을 수 없습니다."),
    INVALID_ACCESS_SCHEDULE("SC0005", "일정에 접근 권한이 없는 사용자입니다.");

    private final String code;
    private final String message;
}

