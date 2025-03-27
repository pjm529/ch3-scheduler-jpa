package com.sparta.api.auth.service;

import com.sparta.api.auth.dto.LoginDto;
import com.sparta.api.auth.dto.MemberReqDto;
import com.sparta.api.member.dto.MemberResDto;

public interface AuthService {
    MemberResDto login(LoginDto dto);

    MemberResDto signUp(MemberReqDto dto);
}
