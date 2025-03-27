package com.sparta.api.schedule.service.impl;

import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.api.schedule.dto.ScheduleDelDto;
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

    private final MemberRepository memberRepository;

    @Override
    public ScheduleResDto saveSchedule(ScheduleReqDto dto) {
        String email = dto.getEmail();
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, email + " 에 해당하는 회원 없음")); // 조회 실패시 throw

        Schedule schedule = new Schedule(dto.getTitle(), dto.getContents(), member); // Schedule 생성
        scheduleRepository.save(schedule); // 일정 저장

        if (schedule.getId() == null) {
            throw new CustomException(CommonExceptionResultMessage.DB_FAIL, "일정 등록에 실패했습니다.");
        }

        return new ScheduleResDto(schedule);
    }

    @Override
    public List<ScheduleResDto> findAllSchedule() {
        List<Schedule> resultList = scheduleRepository.findAll(); // 일정 목록 조회
        return resultList.stream()
                .map(ScheduleResDto::new) // Response 로 mapping
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResDto findScheduleById(Long id) {
        return new ScheduleResDto(this.getSchedule(id));
    }

    @Override
    public ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto) {
        String email = dto.getEmail();
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, email + " 에 해당하는 회원 없음")); // 조회 실패시 throw

        Schedule schedule = this.getSchedule(id);

        if (!member.getEmail().equals(schedule.getMember().getEmail())) { // 이메일 검증
            throw new CustomException(CommonExceptionResultMessage.EMAIL_MISMATCH);
        }

        schedule.update(dto.getTitle(), dto.getContents()); // 정보 update
        scheduleRepository.save(schedule); // 저장
        return new ScheduleResDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, ScheduleDelDto dto) {
        String email = dto.getEmail();
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, email + " 에 해당하는 회원 없음")); // 조회 실패시 throw

        Schedule schedule = this.getSchedule(id);

        if (!member.getEmail().equals(schedule.getMember().getEmail())) { // 이메일 검증
            throw new CustomException(CommonExceptionResultMessage.EMAIL_MISMATCH);
        }

        scheduleRepository.delete(schedule);
    }

    private Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "일정 조회 실패: ID " + id + " 에 해당하는 일정 없음")); // 조회 실패시 throw
    }
}
