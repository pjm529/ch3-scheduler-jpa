package com.sparta.api.member.dto;

import com.sparta.api.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MemberResDto {

    @Schema(description = "PK")
    private Long id;

    @Schema(description = "회원 이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "등록일")
    private String createdDate;

    @Schema(description = "수정일")
    private String modifiedDate;

    public MemberResDto (Member member) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.createdDate = formatter.format(member.getCreatedDate());
        this.modifiedDate = formatter.format(member.getModifiedDate());
    }
}
