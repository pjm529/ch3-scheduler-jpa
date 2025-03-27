package com.sparta.api.member.entity;

import com.sparta.api.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table
@SQLDelete(sql = "update member set deleted = true where id = ?")
@SQLRestriction("deleted = false")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Column(nullable = false)
    private Boolean deleted = false;
}
