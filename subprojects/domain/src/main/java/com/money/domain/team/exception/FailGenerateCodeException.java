package com.money.domain.team.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class FailGenerateCodeException extends MainException {

    public FailGenerateCodeException() {
        super(ErrorCode.FAIL_GENERATE_CODE);
    }
}
