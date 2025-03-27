package com.sparta.api.member.controller;

import com.sparta.api.member.dto.MemberModDto;
import com.sparta.api.member.dto.MemberReqDto;
import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.service.MemberService;
import com.sparta.common.annotation.ApiErrorCodeExamples;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@CrossOrigin("*")
@Tag(name = "Member API", description = "Member 관련 API 모음.")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "회원 등록 API", description = "회원 등록하기 위한 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.DUPLICATE_FAIL
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<MemberResDto> saveMember(@RequestBody @Valid MemberReqDto dto) {
        return BaseResponse.from(memberService.saveMember(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "회원 상세 조회 API", description = "회원 상세 조회하기 위한 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<MemberResDto> findMemberById(@Schema(description = "회원 PK") @PathVariable Long id) {
        return BaseResponse.from(memberService.findMemberById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "회원 수정 API", description = "회원 수정하기 위한 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<MemberResDto> updateMember(
            @Schema(description = "일정 PK") @PathVariable Long id,
            @RequestBody @Valid MemberModDto dto) {
        return BaseResponse.from(memberService.updateMember(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "회원 삭제 API", description = "회원 삭제하기 위한 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<Boolean> deleteMember(@Schema(description = "회원 PK") @PathVariable Long id) {
        memberService.deleteMember(id);
        return BaseResponse.from(true);
    }
}
