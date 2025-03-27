package com.sparta.api.schedule.service;

import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResDto saveSchedule(ScheduleReqDto dto);

    List<ScheduleResDto> findAllSchedule();

    ScheduleResDto findScheduleById(Long id);

    ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto);

    void deleteSchedule(Long id);
}
