package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("김승태");

        //when
        Long joinId = memberService.join(member);

        //then
        org.assertj.core.api.Assertions.assertThat(joinId).isEqualTo(member.getId());
    }
    
    @Test
    @Rollback(value = false)
    public void 중복회원체크() throws Exception{
        //given
        Member member = new Member();
        member.setUsername("박형근");

        Member member2 = new Member();
        member2.setUsername("박형근");

        //when
        memberService.join(member);
        memberService.join(member2);

        //then
        fail("예외발생");

    }

}