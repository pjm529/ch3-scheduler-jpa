package com.sparta.api.schedule.entity;

import com.sparta.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table
@SQLDelete(sql = "update schedule set deleted = true where id = ?")
@SQLRestriction("deleted = false")
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

    public Schedule() {
    }

    public Schedule(String title, String contents, String regNm) {
        this.title = title;
        this.contents = contents;
        this.regNm = regNm;
    }

    public void update(String title, String contents, String regNm) {
        this.title = title;
        this.contents = contents;
        this.regNm = regNm;
    }
}
