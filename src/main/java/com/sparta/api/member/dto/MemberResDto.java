package com.sparta.api.member.dto;

import com.sparta.api.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResDto {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "회원 이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "등록일")
    private LocalDateTime createdDate;

    @Schema(description = "수정일")
    private LocalDateTime modifiedDate;

    public MemberResDto (Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.createdDate = member.getCreatedDate();
        this.modifiedDate = member.getModifiedDate();
    }
}
