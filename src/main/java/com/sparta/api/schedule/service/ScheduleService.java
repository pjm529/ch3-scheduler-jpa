package com.sparta.api.schedule.service;

import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ScheduleService {

    ScheduleResDto saveSchedule(ScheduleReqDto dto, HttpServletRequest request);

    List<ScheduleResDto> findAllSchedule();

    ScheduleResDto findScheduleById(Long id);

    ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto, HttpServletRequest request);

    void deleteSchedule(Long id, HttpServletRequest request);
}
