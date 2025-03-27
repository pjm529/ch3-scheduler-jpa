package com.sparta.api.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class ScheduleReqDto {

    @Schema(description = "일정 제목")
    @NotBlank(message = "일정 제목을 입력해주세요.")
    @Length(max = 10, message = "일정 제목은 최대 10글자까지 입력 가능합니다.")
    private String title;

    @Schema(description = "일정 내용")
    @NotBlank(message = "일정 내용을 입력해주세요.")
    @Length(max = 256, message = "일정 내용은 최대 256글자까지 입력 가능합니다.")
    private String contents;
}
