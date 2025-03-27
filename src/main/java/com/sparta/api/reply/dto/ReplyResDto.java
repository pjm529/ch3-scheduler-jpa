package com.sparta.api.reply.dto;

import com.sparta.api.reply.entity.Reply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ReplyResDto {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "일정 ID")
    private Long scheduleIdd;

    @Schema(description = "댓글 내용")
    private String contents;

    @Schema(description = "작성 유저명")
    private String regNm;

    @Schema(description = "등록일")
    private String createdDate;

    @Schema(description = "수정일")
    private String modifiedDate;

    public ReplyResDto(Reply reply) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.id = reply.getId();
        this.scheduleIdd = reply.getSchedule().getId();
        this.contents = reply.getContents();
        this.regNm = reply.getMember().getName();
        this.createdDate = formatter.format(reply.getCreatedDate());
        this.modifiedDate = formatter.format(reply.getModifiedDate());
    }
}
