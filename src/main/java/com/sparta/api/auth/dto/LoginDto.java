package com.sparta.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class LoginDto {

    @Schema(description = "이메일")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효하지 않는 이메일 형식입니다.")
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
