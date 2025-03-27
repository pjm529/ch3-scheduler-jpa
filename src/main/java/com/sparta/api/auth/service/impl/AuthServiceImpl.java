package com.sparta.api.auth.service.impl;

import com.sparta.api.auth.dto.LoginDto;
import com.sparta.api.auth.dto.MemberReqDto;
import com.sparta.api.auth.service.AuthService;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.component.SystemValues;
import com.sparta.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public void login(LoginDto dto, HttpServletRequest request) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.LOGIN_FAILED));

        if (!StringUtils.equals(dto.getPassword(), member.getPassword())) {
            throw new CustomException(CommonExceptionResultMessage.LOGIN_FAILED);
        }

        HttpSession session = request.getSession();

        // Session에 로그인 회원 정보를 저장한다.
        session.setAttribute(SystemValues.LOGIN_USER.getValue(), new MemberResDto(member));
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

    @Override
    public void logout(HttpServletRequest request) {
        // 로그인하지 않으면 HttpSession이 null로 반환된다.
        HttpSession session = request.getSession(false);
        // 세션이 존재하면 -> 로그인이 된 경우
        if(session != null) {
            session.invalidate(); // 해당 세션(데이터)을 삭제한다.
        }
    }
}
