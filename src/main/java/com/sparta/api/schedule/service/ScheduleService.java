package com.sparta.api.schedule.service;

import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResDto saveSchedule(ScheduleReqDto dto, Long memberId);

    List<ScheduleResDto> findAllSchedule();

    ScheduleResDto findScheduleById(Long id);

    ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto, Long memberId);

    void deleteSchedule(Long id, Long memberId);
}
