package com.sparta.api.member.service.impl;

import com.sparta.api.member.dto.PasswordUpdateDto;
import com.sparta.api.member.dto.MemberUpdateDto;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.api.member.service.MemberService;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("memberService")
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberResDto updateMember(MemberUpdateDto dto, Long memberId) {
        Member member = this.getMember(memberId); // Member 조회
        member.update(dto.getName()); // update
        memberRepository.save(member); // 멤버 수정
        return new MemberResDto(member);
    }

    @Override
    public void deleteMember(Long memberId) {
        Member member = this.getMember(memberId); // Member 조회
        memberRepository.delete(member); // Member 삭제
    }

    @Override
    public void updatePassword(PasswordUpdateDto dto, Long memberId) {
        String newPw = dto.getNewPw();
        String currentPw = dto.getCurrentPw();

        Member member = this.getMember(memberId); // Member 조회

        if (!passwordEncoder.matches(currentPw, member.getPassword())) { // 비밀번호 검증
            throw new CustomException(CommonExceptionResultMessage.PW_MISMATCH);
        }

        if (StringUtils.equals(newPw, currentPw)) {
            throw new CustomException(CommonExceptionResultMessage.VALID_FAIL, "현재 사용 중인 비밀번호입니다.");
        }

        String encodePw = passwordEncoder.encode(newPw); // 비밀번호 암호화
        member.updatePw(encodePw);
        memberRepository.save(member);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + memberId + " 에 해당하는 회원 없음")); // 조회 실패시 throw
    }
}