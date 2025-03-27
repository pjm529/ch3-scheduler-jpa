package com.sparta.api.member.service.impl;

import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberReqDto;
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
    public MemberResDto saveMember(MemberReqDto dto) {
        String email = dto.getEmail();

        Optional<Member> memberOpt = memberRepository.findByEmail(email);
        if (memberOpt.isPresent()) {
            throw new CustomException(CommonExceptionResultMessage.DUPLICATE_FAIL, "이미 사용 중인 이메일입니다.");
        }

        Member member = new Member(dto.getName(), email, dto.getPassword());
        memberRepository.save(member);

        if (member.getId() == null) {
            throw new CustomException(CommonExceptionResultMessage.DB_FAIL, "회원 등록에 실패했습니다.");
        }

        return new MemberResDto(member);
    }

    @Override
    public MemberResDto findMemberById(Long id) {
        return new MemberResDto(this.getMember(id));
    }

    @Override
    public MemberResDto updateMember(Long id, MemberModDto dto) {
        Member member = this.getMember(id);
        member.update(dto.getName());
        memberRepository.save(member);
        return new MemberResDto(member);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = this.getMember(id);
        memberRepository.delete(member);
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + id + " 에 해당하는 회원 없음")); // 조회 실패시 throw
    }
}
