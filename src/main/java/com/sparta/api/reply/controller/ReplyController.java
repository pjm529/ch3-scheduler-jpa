package com.sparta.api.reply.controller;

import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.reply.dto.ReplyReqDto;
import com.sparta.api.reply.dto.ReplyResDto;
import com.sparta.api.reply.dto.ReplyUpdateDto;
import com.sparta.api.reply.service.ReplyService;
import com.sparta.common.annotation.ApiErrorCodeExamples;
import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.component.SystemValues;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
@Tag(name = "Reply API", description = "Reply 관련 API 모음.")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    @Operation(summary = "댓글 등록 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.AUTHENTICATION_FAILED
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<ReplyResDto> saveReply(@RequestBody @Valid ReplyReqDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberResDto sessionMember = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue()); // sessionMember
        return BaseResponse.from(replyService.saveReply(dto, sessionMember.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "댓글 상세 조회 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.AUTHENTICATION_FAILED
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<ReplyResDto> findReplyById(@Schema(description = "댓글 PK") @PathVariable Long id) {
        return BaseResponse.from(replyService.findReplyById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "댓글 수정 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.VALID_FAIL
            , CommonExceptionResultMessage.ACCESS_DENIED
            , CommonExceptionResultMessage.AUTHENTICATION_FAILED
            , CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<ReplyResDto> updateReply(@Schema(description = "댓글 PK") @PathVariable Long id,
                                                 @RequestBody @Valid ReplyUpdateDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberResDto sessionMember = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue()); // sessionMember

        return BaseResponse.from(replyService.updateReply(id, dto, sessionMember.getId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "댓글 삭제 API")
    @ApiErrorCodeExamples({CommonExceptionResultMessage.NOT_FOUND
            , CommonExceptionResultMessage.ACCESS_DENIED
            , CommonExceptionResultMessage.AUTHENTICATION_FAILED
            , CommonExceptionResultMessage.DB_FAIL
            , CommonExceptionResultMessage.FAIL
    })
    public BaseResponse<Boolean> deleteReply(@Schema(description = "댓글 PK") @PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberResDto sessionMember = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue()); // sessionMember
        replyService.deleteReply(id, sessionMember.getId());
        return BaseResponse.from(true);
    }
}
