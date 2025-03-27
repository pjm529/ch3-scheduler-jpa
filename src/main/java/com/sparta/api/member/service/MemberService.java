package com.sparta.api.member.service;

import com.sparta.api.member.dto.MemberDelDto;
import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberResDto;

public interface MemberService {


    MemberResDto findMemberById(Long id);

    MemberResDto updateMember(Long id, MemberModDto dto);

    void deleteMember(Long id, MemberDelDto dto);
}
