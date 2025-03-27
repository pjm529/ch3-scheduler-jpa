package com.sparta.api.reply.entity;

import com.sparta.api.common.entity.BaseTimeEntity;
import com.sparta.api.member.entity.Member;
import com.sparta.api.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(indexes = {
        @Index(name = "idx_reply_01", columnList = "schedule_id"),
        @Index(name = "idx_reply_02", columnList = "member_id"),
})
@SQLDelete(sql = "update reply set deleted = true where id = ?")
@SQLRestriction("deleted = false")
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1024)
    private String contents; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule; // 일정 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 댓글 작성자 정보

    @Column(nullable = false)
    private Boolean deleted;

    public Reply() {
    }

    public Reply(String contents, Schedule schedule, Member member) {
        this.contents = contents;
        this.schedule = schedule;
        this.member = member;
        this.deleted = false;
    }
}
