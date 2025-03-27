package com.sparta.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberModDto {

    @Schema(description = "회원 이름")
    @NotBlank(message = "회원 이름을 입력해주세요.")
    private String name;
}
