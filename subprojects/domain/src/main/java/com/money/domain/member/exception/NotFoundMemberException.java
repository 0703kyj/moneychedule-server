package com.money.domain.member.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class NotFoundMemberException extends MainException {

    public NotFoundMemberException() {
        super(ErrorCode.NOT_FOUND_MEMBER);
    }
}
