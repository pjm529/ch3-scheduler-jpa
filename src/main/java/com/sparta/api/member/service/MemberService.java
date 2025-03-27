package com.sparta.api.member.service;

import com.sparta.api.member.dto.PasswordUpdateDto;
import com.sparta.api.member.dto.MemberUpdateDto;
import com.sparta.api.member.dto.MemberResDto;

public interface MemberService {

    MemberResDto updateMember(MemberUpdateDto dto, Long memberId);

    void deleteMember(Long memberId);

    void updatePassword(PasswordUpdateDto dto, Long memberId);
}
