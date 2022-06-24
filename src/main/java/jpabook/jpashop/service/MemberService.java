package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final로 선언되어있는 변수만 생성자에 선언해줌
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        List<Member> findMember = memberRepository.findByName(member.getUsername());
        if(findMember.size() > 0){
            throw new IllegalStateException("이미 존재하는 회원이 있습니다");
        }
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 특정 회원 조회
     * @param id
     * @return
     */
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
}
