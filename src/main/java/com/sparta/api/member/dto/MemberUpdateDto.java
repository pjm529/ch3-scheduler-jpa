package com.sparta.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberUpdateDto {

    @Schema(description = "회원 이름")
    @NotBlank(message = "회원 이름을 입력해주세요.")
    @Max(value = 10, message = "이름은 10글자까지 입력가능합니다.")
    private String name;
}
