package com.money.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.money.domain.InvitedCode;
import com.money.domain.Member;
import com.money.exception.ErrorCode;
import com.money.exception.ExpiredCodeException;
import com.money.repository.MemberRepository;
import com.money.util.Platform;
import java.util.Date;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("유효기간이 만료된 연결 코드는 예외를 던진다.")
    void expirationCodeException() {
        //TODO
        InvitedCode invitedCode = new InvitedCode("12345",
                new Date((new Date()).getTime() - 86400));
        Member member = Member.of("abc@naver.com", "fdsf", Platform.EMAIL);
        member.allocateInvitedCode(invitedCode);

        when(memberRepository.findByInvitedCode(any())).thenReturn(Optional.of(member));

        String findCode = member.getInvitedCode().getCode();
        assertThatThrownBy(() -> memberService.validateInvitedCode(findCode))
                .isInstanceOf(ExpiredCodeException.class);
    }

    @Test
    @DisplayName("유효기간이 만료되지 않은 연결 코드는 연결 코드를 반환한다.")
    void validCode() {
        //TODO
        InvitedCode invitedCode = new InvitedCode("12345",
                new Date((new Date()).getTime() + 86400));
        Member member = Member.of("abc@naver.com", "fdsf", Platform.EMAIL);
        member.allocateInvitedCode(invitedCode);

        when(memberRepository.findByInvitedCode(any())).thenReturn(Optional.of(member));

        String findCode = memberService.validateInvitedCode(invitedCode.getCode());
        assertThat(findCode).isEqualTo(invitedCode.getCode());
    }
}
