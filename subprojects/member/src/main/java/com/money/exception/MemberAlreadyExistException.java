package com.money.exception;

public class MemberAlreadyExistException extends MainException{

    public MemberAlreadyExistException() {
        super(ErrorCode.ALREADY_EXIST_MEMBER);
    }
}
