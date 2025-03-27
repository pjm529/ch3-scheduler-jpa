package com.sparta.api.schedule.repository;

import com.sparta.api.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {
    @Query("select s from Schedule s join s.member m where s.id = :id and m.deleted = false")
    Optional<Schedule> findByIdWithActiveMember(@Param("id") Long id);
}
