package com.sparta.api.schedule.service;

import com.sparta.api.schedule.dto.ScheduleListDto;
import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.common.component.CustomPageable;
import com.sparta.common.component.PaginationResDto;


public interface ScheduleService {

    ScheduleResDto saveSchedule(ScheduleReqDto dto, Long memberId);

    PaginationResDto<ScheduleListDto> findAllSchedule(CustomPageable customPageable);

    ScheduleResDto findScheduleById(Long id);

    ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto, Long memberId);

    void deleteSchedule(Long id, Long memberId);
}
