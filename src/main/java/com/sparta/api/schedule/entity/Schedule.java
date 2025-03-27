package com.sparta.api.schedule.entity;

import com.sparta.api.common.entity.BaseTimeEntity;
import com.sparta.api.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Getter
@Table(indexes = {
        @Index(name = "idx_schedule_01", columnList = "member_id"),
})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean deleted = false;

    public Schedule() {
    }

    public Schedule(String title, String contents, Member member) {
        this.title = title;
        this.contents = contents;
        this.member = member;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
