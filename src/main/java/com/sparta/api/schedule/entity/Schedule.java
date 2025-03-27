package com.sparta.api.schedule.entity;

import com.sparta.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // 할일 제목

    @Column(nullable = false)
    private String contents; // 할일 내용

    @Column(nullable = false)
    private String regNm; // 작성 유저명

    @Column(nullable = false)
    private Boolean deleted = false;
}
