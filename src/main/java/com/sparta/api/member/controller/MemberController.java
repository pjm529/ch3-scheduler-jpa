package com.sparta.api.member.controller;

import com.sparta.api.member.dto.MemberDelDto;
import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.service.MemberService;
import com.sparta.common.annotation.ApiErrorCodeExamples;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.component.SystemValues;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@CrossOrigin("*")
@Tag(name = "내 정보 관련 API", description = "내 정보 관련 API 모음.")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.AUTHENTICATION_FAILED
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<MemberResDto> getMyInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberResDto sessionMember = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue());
        return BaseResponse.from(sessionMember);
    }

    @PutMapping("/me")
    @Operation(summary = "회원 수정 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.AUTHENTICATION_FAILED
            , CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<MemberResDto> updateMember(@RequestBody @Valid MemberModDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberResDto sessionMember = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue());
        MemberResDto updatedMember = memberService.updateMember(dto, sessionMember.getId()); // 정보 수정

        // 세션 무효화 없이 기존 세션에 속성 값만 업데이트
        session.setAttribute(SystemValues.LOGIN_USER.getValue(), updatedMember);
        return BaseResponse.from(updatedMember);
    }

    @DeleteMapping("/me")
    @Operation(summary = "회원 삭제 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.AUTHENTICATION_FAILED
            , CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<Boolean> deleteMember(@RequestBody @Valid MemberDelDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberResDto sessionMember = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue());
        memberService.deleteMember(dto, sessionMember.getId()); // 삭제
        session.invalidate(); // 해당 세션(데이터)을 삭제한다.
        return BaseResponse.from(true);
    }
}
