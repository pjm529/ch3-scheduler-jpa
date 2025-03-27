package com.sparta.api.schedule.dto;

import com.sparta.api.schedule.entity.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ScheduleListDto {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "일정 제목")
    private String title;

    @Schema(description = "일정 내용")
    private String contents;

    @Schema(description = "작성 유저명")
    private String regNm;

    @Schema(description = "등록일")
    private String createdDate;

    @Schema(description = "수정일")
    private String modifiedDate;

    @Schema(description = "댓글 개수")
    private int replyCnt;

    public ScheduleListDto(Schedule schedule) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.regNm = schedule.getMember().getName();
        this.createdDate = formatter.format(schedule.getCreatedDate());
        this.modifiedDate = formatter.format(schedule.getModifiedDate());
        this.replyCnt = schedule.getReplyList().size();
    }
}
