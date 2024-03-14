package com.money.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MainException extends RuntimeException{
    private final ErrorCode errorCode;
}
