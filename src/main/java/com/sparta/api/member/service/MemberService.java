package com.sparta.api.member.service;

import com.sparta.api.member.dto.MemberReqDto;
import com.sparta.api.member.dto.MemberResDto;

public interface MemberService {

    MemberResDto saveMember(MemberReqDto dto);

    MemberResDto findMemberById(Long id);
}
