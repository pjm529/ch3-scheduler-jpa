package com.sparta.api.member.controller;

import com.sparta.api.member.dto.MemberDelDto;
import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.service.MemberService;
import com.sparta.common.annotation.ApiErrorCodeExamples;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
        return BaseResponse.from(memberService.getMyInfo(request));
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
        return BaseResponse.from(memberService.updateMember(dto, request));
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
        memberService.deleteMember(dto, request);
        return BaseResponse.from(true);
    }
}
