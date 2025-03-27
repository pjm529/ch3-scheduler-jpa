package com.sparta.api.auth.service.impl;

import com.sparta.api.auth.dto.LoginDto;
import com.sparta.api.auth.dto.MemberReqDto;
import com.sparta.api.auth.service.AuthService;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("authService")
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResDto login(LoginDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.LOGIN_FAILED));

        if (!StringUtils.equals(dto.getPassword(), member.getPassword())) {
            throw new CustomException(CommonExceptionResultMessage.LOGIN_FAILED);
        }

        return new MemberResDto(member);
    }

    @Override
    public MemberResDto signUp(MemberReqDto dto) {
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
}
