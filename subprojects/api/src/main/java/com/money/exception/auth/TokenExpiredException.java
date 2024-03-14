package com.money.exception.auth;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class TokenExpiredException extends MainException {

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
