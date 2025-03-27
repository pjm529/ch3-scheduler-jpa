package com.sparta.api.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleReqDto {

    @Schema(description = "일정 제목")
    @NotBlank(message = "일정 제목을 입력해주세요.")
    private String title;

    @Schema(description = "일정 내용")
    @NotBlank(message = "일정 내용을 입력해주세요.")
    private String contents;
}
