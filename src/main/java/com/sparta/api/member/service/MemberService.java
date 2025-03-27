package com.sparta.api.member.service;

import com.sparta.api.member.dto.MemberDelDto;
import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberResDto;

public interface MemberService {

    MemberResDto updateMember(MemberModDto dto, Long memberId);

    void deleteMember(MemberDelDto dto, Long memberId);
}
