package com.sparta.api.auth.controller;

import com.sparta.api.auth.dto.LoginDto;
import com.sparta.api.auth.service.AuthService;
import com.sparta.api.auth.dto.MemberReqDto;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.common.annotation.ApiErrorCodeExample;
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
@RequestMapping("/api/auth")
@CrossOrigin("*")
@Tag(name = "Auth API", description = "Auth 관련 API 모음.")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.LOGIN_FAILED
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<Boolean> login(@RequestBody @Valid LoginDto dto, HttpServletRequest request) {
        MemberResDto memberResDto = authService.login(dto);

        HttpSession session = request.getSession(); // Session 을 가져온다.

        // Session 에 로그인 회원 정보를 저장한다.
        session.setAttribute(SystemValues.LOGIN_USER.getValue(), memberResDto);
        return BaseResponse.from(true);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원 등록 API", description = "회원 등록하기 위한 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.DUPLICATE_FAIL
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<MemberResDto> signUp(@RequestBody @Valid MemberReqDto dto) {
        return BaseResponse.from(authService.signUp(dto));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API", description = "로그아웃 API")
    @ApiErrorCodeExample(CommonExceptionResultMessage.FAIL)
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 미 로그인 시 null 반환
        if (session != null) {
            session.invalidate(); // 해당 세션(데이터)을 삭제한다.
        }
        return BaseResponse.from(true);
    }
}
