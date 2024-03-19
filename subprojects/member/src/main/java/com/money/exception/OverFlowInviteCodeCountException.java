package com.money.exception;

public class OverFlowInviteCodeCountException extends MainException{

    public OverFlowInviteCodeCountException() {
        super(ErrorCode.OVER_FLOW_INVITE_CODE);
    }
}
