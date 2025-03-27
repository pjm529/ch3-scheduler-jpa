package com.sparta.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class MemberUpdateDto {

    @Schema(description = "회원 이름")
    @NotBlank(message = "회원 이름을 입력해주세요.")
    @Length(max = 10, message = "이름은 10글자까지 입력가능합니다.")
    private String name;
}
