package com.sparta.api.member.service;

import com.sparta.api.member.dto.MemberDelDto;
import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberResDto;
import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {

    MemberResDto getMyInfo(HttpServletRequest request);

    MemberResDto updateMember(MemberModDto dto, HttpServletRequest request);

    void deleteMember(MemberDelDto dto, HttpServletRequest request);
}
