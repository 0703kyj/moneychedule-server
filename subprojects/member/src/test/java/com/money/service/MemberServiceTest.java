package com.money.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.money.domain.InvitedCode;
import com.money.domain.Member;
import com.money.exception.ExpiredCodeException;
import com.money.exception.MemberAlreadyExistException;
import com.money.repository.MemberRepository;
import com.money.util.Platform;
import java.util.Date;
import java.util.Optional;
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
    @DisplayName("멤버를 저장한다.")
    void saveMember() {
        Member member = Member.of("abc@naver.com", "fdsf", Platform.EMAIL);

        when(memberRepository.save(any())).thenReturn(member);
        when(memberRepository.findByEmailAndPlatform(any(), any())).thenReturn(Optional.empty());

        Member saveMember = memberService.saveMember(member);
        assertThat(saveMember).isEqualTo(member);
    }

    @Test
    @DisplayName("같은 이메일을 가진 멤버가 존재하는 경우 예외를 반환한다,")
    void alreadyExistMemberException() {
        Member member = Member.of("abc@naver.com", "fdsf", Platform.EMAIL);

        when(memberRepository.findByEmailAndPlatform(any(), any())).thenReturn(Optional.of(member));

        assertThatThrownBy(() -> memberService.saveMember(member))
                .isInstanceOf(MemberAlreadyExistException.class);
    }

    @Test
    @DisplayName("유효기간이 만료된 연결 코드는 예외를 던진다.")
    void expirationCodeException() {
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
        InvitedCode invitedCode = new InvitedCode("12345",
                new Date((new Date()).getTime() + 86400));
        Member member = Member.of("abc@naver.com", "fdsf", Platform.EMAIL);
        member.allocateInvitedCode(invitedCode);

        when(memberRepository.findByInvitedCode(any())).thenReturn(Optional.of(member));

        String findCode = memberService.validateInvitedCode(invitedCode.getCode());
        assertThat(findCode).isEqualTo(invitedCode.getCode());
    }
}
