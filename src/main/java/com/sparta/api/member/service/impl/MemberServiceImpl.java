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

import java.util.Optional;

@Service("memberService")
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResDto findMemberById(Long id) {
        return new MemberResDto(this.getMember(id));
    }

    @Override
    public MemberResDto updateMember(Long id, MemberModDto dto) {
        Member member = this.getMember(id);
        if (!member.getPassword().equals(dto.getPassword())) {
            throw new CustomException(CommonExceptionResultMessage.PW_MISMATCH);
        }
        member.update(dto.getName());
        memberRepository.save(member);
        return new MemberResDto(member);
    }

    @Override
    public void deleteMember(Long id, MemberDelDto dto) {
        Member member = this.getMember(id);
        if (!member.getPassword().equals(dto.getPassword())) {
            throw new CustomException(CommonExceptionResultMessage.PW_MISMATCH);
        }
        memberRepository.delete(member);
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + id + " 에 해당하는 회원 없음")); // 조회 실패시 throw
    }
}
