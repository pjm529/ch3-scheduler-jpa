package com.sparta.api.schedule.service.impl;

import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.api.schedule.dto.ScheduleListDto;
import com.sparta.api.schedule.dto.ScheduleReqDto;
import com.sparta.api.schedule.dto.ScheduleResDto;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import com.sparta.api.schedule.repository.specification.ScheduleSpecification;
import com.sparta.api.schedule.service.ScheduleService;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.component.CustomPageable;
import com.sparta.common.component.PaginationResDto;
import com.sparta.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public ScheduleResDto saveSchedule(ScheduleReqDto dto, Long memberId) {
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
    public PaginationResDto<ScheduleListDto> findAllSchedule(CustomPageable customPageable) {
        Pageable pageable = customPageable.getPageable(); // page 생성

        Specification<Schedule> spec = ScheduleSpecification.buildSearchSpecification(); //  ScheduleSpec 생성

        Page<Schedule> result = scheduleRepository.findAll(spec, pageable); // 목록 조회

        List<ScheduleListDto> resultList = result.stream()
                .map(ScheduleListDto::new)
                .collect(Collectors.toList());

        return PaginationResDto.<ScheduleListDto>builder()
                .data(resultList) // data
                .total(result.getTotalElements()) // 총 데이터 수
                .size(customPageable.getSize()) // 페이지 표시 수
                .page(customPageable.getPage()) // 페이지
                .totalPages((result.getTotalElements() + customPageable.getSize() - 1) / customPageable.getSize()) // 총 페이지 수
                .build();
    }

    @Override
    public ScheduleResDto findScheduleById(Long id) {
        return new ScheduleResDto(this.getSchedule(id));
    }

    @Override
    public ScheduleResDto updateSchedule(Long id, ScheduleReqDto dto, Long memberId) {
        Schedule schedule = this.validMember(id, memberId);
        schedule.update(dto.getTitle(), dto.getContents()); // 정보 update
        scheduleRepository.save(schedule); // 저장
        return new ScheduleResDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, Long memberId) {
        Schedule schedule = this.validMember(id, memberId);
        scheduleRepository.delete(schedule);
    }

    private Schedule getSchedule(Long id) {
        return scheduleRepository.findByIdWithActiveMember(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "일정 조회 실패: ID " + id + " 에 해당하는 일정 없음")); // 조회 실패시 throw
    }

    private Schedule validMember(Long id, Long memberId) {
        Schedule schedule = this.getSchedule(id); // 일정 조회

        if (!memberId.equals(schedule.getMember().getId())) { // 회원 검증
            throw new CustomException(CommonExceptionResultMessage.ACCESS_DENIED);
        }

        return schedule;
    }
}
