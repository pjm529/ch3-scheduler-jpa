package com.sparta.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class PasswordUpdateDto {
    @Schema(description = "기존 비밀번호")
    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private String currentPw;

    @Schema(description = "새 비밀번호")
    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$",
            message = "비밀번호는 8~20자 사이여야 하며, 소문자, 대문자, 숫자, 특수문자를 각각 최소 한 개 이상 포함해야 합니다."
    )
    private String newPw;
}
