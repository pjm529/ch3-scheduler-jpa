package com.sparta.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class PasswordUpdateDto {
    @Schema(description = "기존 비밀번호")
    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private String currentPw;

    @Schema(description = "새 비밀번호")
    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private String newPw;
}
