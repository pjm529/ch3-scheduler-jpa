package com.sparta.api.auth.service;

import com.sparta.api.auth.dto.LoginDto;
import com.sparta.api.auth.dto.MemberReqDto;
import com.sparta.api.member.dto.MemberResDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    void login(LoginDto dto, HttpServletRequest request);

    MemberResDto signUp(MemberReqDto dto);

    void logout(HttpServletRequest request);

}
