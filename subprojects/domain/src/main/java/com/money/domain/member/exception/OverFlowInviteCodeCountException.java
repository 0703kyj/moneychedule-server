package com.money.domain.member.exception;

import com.money.exception.ErrorCode;
import com.money.exception.MainException;

public class OverFlowInviteCodeCountException extends MainException {

    public OverFlowInviteCodeCountException() {
        super(ErrorCode.OVER_FLOW_INVITE_CODE);
    }
}
