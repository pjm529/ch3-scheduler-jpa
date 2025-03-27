package com.sparta.api.schedule.repository.specification;

import com.sparta.api.member.entity.Member;
import com.sparta.api.schedule.entity.Schedule;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ScheduleSpecification {

    public static Specification<Schedule> buildSearchSpecification() {
        return (root, query, criteriaBuilder) -> {
            // Schedule과 연관된 Member 조인
            Join<Schedule, Member> memberJoin = root.join("member", JoinType.INNER);
            // Member의 deleted 필드가 false 인 조건 생성
            Predicate activeMemberPredicate = criteriaBuilder.isFalse(memberJoin.get("deleted"));

            // 필요에 따라 추가 Predicate를 넣을 수 있습니다.
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(activeMemberPredicate);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}