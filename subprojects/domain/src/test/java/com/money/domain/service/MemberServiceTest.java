package com.money.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.money.domain.member.entity.Member;
import com.money.domain.member.exception.MemberAlreadyExistException;
import com.money.domain.member.repository.MemberRepository;
import com.money.domain.member.service.MemberService;
import com.money.domain.member.entity.enums.Platform;
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
}
