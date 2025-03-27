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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ScheduleResDto> findAllSchedule() {
        List<Schedule> resultList = scheduleRepository.findAll();
        return resultList.stream()
                .map(ScheduleResDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResDto findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .map(ScheduleResDto::new) // 일정 조회 후 mapping
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "일정 조회 실패: ID " + id + " 에 해당하는 일정 없음"));
    }
}
