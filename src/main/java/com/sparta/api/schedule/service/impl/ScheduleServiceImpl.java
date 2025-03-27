package com.sparta.api.schedule.service.impl;

import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import com.sparta.api.schedule.service.ScheduleService;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scheduleService")
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResDto saveSchedule(ScheduleReqDto dto) {
        Schedule schedule = new Schedule(dto.getTitle(), dto.getContents(), dto.getRegNm());
        scheduleRepository.save(schedule);

        if (schedule.getId() == null) {
            throw new CustomException(CommonExceptionResultMessage.DB_FAIL, "일정 등록에 실패했습니다.");
        }

        return new ScheduleResDto(schedule);
    }
}
