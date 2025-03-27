package com.sparta.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberReqDto {

    @Schema(description = "회원 이름")
    @NotBlank(message = "회원 이름을 입력해주세요.")
    @Max(value = 10, message = "이름은 10글자까지 입력가능합니다.")
    private String name;

    @Schema(description = "이메일")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효하지 않는 이메일 형식입니다.")
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Min(value = 8, message = "비밀번호는 8 ~ 16글자 입력해주세요.")
    @Max(value = 16, message = "비밀번호는 8 ~ 16글자 입력해주세요.")
    private String password;
}
