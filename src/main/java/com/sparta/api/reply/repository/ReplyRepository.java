package com.sparta.api.reply.repository;

import com.sparta.api.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r join r.member m join r.schedule s where r.id = :id and m.deleted = false and s.deleted = false")
    Optional<Reply> findByIdWithActiveMemberAndActiveSchedule(@Param("id") Long id);
}
