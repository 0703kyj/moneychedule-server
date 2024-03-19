package com.money.service.member;

import com.money.domain.InviteCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberFollowServiceTest {

    @InjectMocks
    MemberFollowService memberFollowService;

    @Test
    @DisplayName("초대 코드는 중복되면 안된다.")
    void getInviteCode() {
        //TODO

        for (int i = 0;i<10;i++){
            InviteCode inviteCode = memberFollowService.getInviteCode();
            System.out.println("inviteCode.getCode() = " + inviteCode.getCode());
        }
    }
}
