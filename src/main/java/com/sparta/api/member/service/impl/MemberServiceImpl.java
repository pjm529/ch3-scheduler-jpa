package com.sparta.api.member.service.impl;

import com.sparta.api.member.dto.MemberDelDto;
import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.api.member.service.MemberService;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("memberService")
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResDto updateMember(MemberModDto dto, Long memberId) {
        Member member = this.getMember(memberId); // Member 조회
        member.update(dto.getName()); // update
        memberRepository.save(member); // 멤버 수정
        return new MemberResDto(member);
    }

    @Override
    public void deleteMember(MemberDelDto dto, Long memberId) {
        Member member = this.getMember(memberId); // Member 조회
        memberRepository.delete(member); // Member 삭제
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + memberId + " 에 해당하는 회원 없음")); // 조회 실패시 throw
    }
}
