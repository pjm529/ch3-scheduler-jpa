package com.sparta.api.auth.service.impl;

import com.sparta.api.auth.dto.LoginDto;
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
}
