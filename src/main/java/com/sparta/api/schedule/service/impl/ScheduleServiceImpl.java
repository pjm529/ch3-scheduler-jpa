package com.sparta.api.schedule.service.impl;

import com.sparta.api.member.dto.MemberResDto;
import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import com.sparta.api.schedule.service.ScheduleService;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.component.SystemValues;
import com.sparta.common.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ScheduleResDto saveSchedule(ScheduleReqDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        MemberResDto memberResDto = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue());

        Long memberId = memberResDto.getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + memberId + " 에 해당하는 회원 없음")); // 조회 실패시 throw

        Schedule schedule = new Schedule(dto.getTitle(), dto.getContents(), member); // Schedule 생성
        scheduleRepository.save(schedule); // 일정 저장

        if (schedule.getId() == null) {
            throw new CustomException(CommonExceptionResultMessage.DB_FAIL, "일정 등록에 실패했습니다.");
        }

        return new ScheduleResDto(schedule);
    }

    @Override
    public List<ScheduleResDto> findAllSchedule() {
        List<Schedule> resultList = scheduleRepository.findAllWithActiveMember(); // 일정 목록 조회
        return resultList.stream()
                .map(ScheduleResDto::new) // Response 로 mapping
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResDto findScheduleById(Long id) {
        return new ScheduleResDto(this.getSchedule(id));
    }

    @Override
    public ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto, HttpServletRequest request) {
        Schedule schedule = this.validMember(id, request);
        schedule.update(dto.getTitle(), dto.getContents()); // 정보 update
        scheduleRepository.save(schedule); // 저장
        return new ScheduleResDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, HttpServletRequest request) {
        Schedule schedule = this.validMember(id, request);
        scheduleRepository.delete(schedule);
    }

    private Schedule getSchedule(Long id) {
        return scheduleRepository.findByIdWithActiveMember(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "일정 조회 실패: ID " + id + " 에 해당하는 일정 없음")); // 조회 실패시 throw
    }

    private Schedule validMember(Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        MemberResDto memberResDto = (MemberResDto) session.getAttribute(SystemValues.LOGIN_USER.getValue());

        Long memberId = memberResDto.getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + memberId + " 에 해당하는 회원 없음")); // 조회 실패시 throw

        Schedule schedule = this.getSchedule(id);

        if (!memberId.equals(schedule.getId())) { // 회원 검증
            throw new CustomException(CommonExceptionResultMessage.ACCESS_DENIED);
        }

        return schedule;
    }
}
