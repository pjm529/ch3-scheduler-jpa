package com.sparta.api.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class ScheduleDelDto {

    @Schema(description = "작성자 이메일")
    @NotBlank(message = "작성자 이메일을 입력해주세요.")
    @Email(message = "유효하지 않는 이메일 형식입니다.")
    private String email;
}
