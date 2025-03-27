package com.sparta.api.auth.controller;

import com.sparta.api.auth.dto.LoginDto;
import com.sparta.api.auth.service.AuthService;
import com.sparta.api.auth.dto.MemberReqDto;
import com.sparta.api.member.dto.MemberResDto;
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
        authService.login(dto, request);
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
}
