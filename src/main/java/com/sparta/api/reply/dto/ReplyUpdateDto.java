package com.sparta.api.reply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class ReplyUpdateDto {

    @Schema(description = "댓글 내용")
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Length(max = 256, message = "댓글 내용은 최대 256글자까지 입력 가능합니다.")
    private String contents;
}
