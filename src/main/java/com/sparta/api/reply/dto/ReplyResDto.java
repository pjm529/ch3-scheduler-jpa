package com.sparta.api.reply.dto;

import com.sparta.api.reply.entity.Reply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResDto {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "일정 ID")
    private Long scheduleId;

    @Schema(description = "댓글 내용")
    private String contents;

    @Schema(description = "작성 유저명")
    private String regNm;

    @Schema(description = "등록일")
    private LocalDateTime createdDate;

    @Schema(description = "수정일")
    private LocalDateTime modifiedDate;

    public ReplyResDto(Reply reply) {
        this.id = reply.getId();
        this.scheduleId = reply.getSchedule().getId();
        this.contents = reply.getContents();
        this.regNm = reply.getMember().getName();
        this.createdDate = reply.getCreatedDate();
        this.modifiedDate = reply.getModifiedDate();
    }
}
