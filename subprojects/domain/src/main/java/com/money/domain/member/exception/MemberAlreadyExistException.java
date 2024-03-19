package com.money.domain.member.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class MemberAlreadyExistException extends MainException {

    public MemberAlreadyExistException() {
        super(ErrorCode.ALREADY_EXIST_MEMBER);
    }
}
