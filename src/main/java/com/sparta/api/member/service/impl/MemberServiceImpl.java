package com.sparta.api.member.service.impl;

import com.sparta.api.member.dto.MemberDelDto;
import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.api.member.service.MemberService;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.component.SystemValues;
import com.sparta.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("memberService")
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResDto getMyInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue());
    }

    @Override
    public MemberResDto updateMember(MemberModDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = this.getMember(session); // Member 조회
        member.update(dto.getName()); // update
        memberRepository.save(member); // 멤버 수정

        session.invalidate(); // 해당 세션(데이터)을 삭제한다.

        HttpSession newSession = request.getSession(true); // 새로운 세션 생성
        MemberResDto memberResDto = new MemberResDto(member);
        newSession.setAttribute(SystemValues.LOGIN_USER.getValue(), memberResDto); // 새로운 Member 정보 Session 저장
        return memberResDto;
    }

    @Override
    public void deleteMember(MemberDelDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = this.getMember(session); // Member 조회
        memberRepository.delete(member); // Member 삭제

        session.invalidate(); // 해당 세션(데이터)을 삭제한다.
    }

    private Member getMember(HttpSession session) {
        MemberResDto memberResDto = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue());

        Long memberId = memberResDto.getId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + memberId + " 에 해당하는 회원 없음")); // 조회 실패시 throw
    }
}
