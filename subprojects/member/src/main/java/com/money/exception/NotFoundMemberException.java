package com.money.exception;

public class NotFoundMemberException extends MainException{

    public NotFoundMemberException() {
        super(ErrorCode.NOT_FOUND_MEMBER);
    }
}
