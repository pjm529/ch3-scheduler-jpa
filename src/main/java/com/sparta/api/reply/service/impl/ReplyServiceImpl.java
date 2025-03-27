package com.sparta.api.reply.service.impl;

import com.sparta.api.member.entity.Member;
import com.sparta.api.member.repository.MemberRepository;
import com.sparta.api.reply.dto.ReplyReqDto;
import com.sparta.api.reply.dto.ReplyResDto;
import com.sparta.api.reply.dto.ReplyUpdateDto;
import com.sparta.api.reply.entity.Reply;
import com.sparta.api.reply.repository.ReplyRepository;
import com.sparta.api.reply.service.ReplyService;
import com.sparta.api.schedule.entity.Schedule;
import com.sparta.api.schedule.repository.ScheduleRepository;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("replyService")
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final MemberRepository memberRepository;

    private final ScheduleRepository scheduleRepository;

    @Override
    public ReplyResDto saveReply(ReplyReqDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + memberId + " 에 해당하는 회원 없음")); // 조회 실패시 throw

        Long scheduleId = dto.getScheduleId();
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "일정 조회 실패: ID " + scheduleId + " 에 해당하는 일정 없음")); // 조회 실패시 throw


        Reply reply = new Reply(dto.getContents(), schedule, member);
        replyRepository.save(reply);

        if (reply.getId() == null) {
            throw new CustomException(CommonExceptionResultMessage.DB_FAIL, "댓글 등록에 실패했습니다.");
        }

        return new ReplyResDto(reply);
    }

    @Override
    public ReplyResDto findReplyById(Long id) {
        return new ReplyResDto(this.getReply(id));
    }

    @Override
    public ReplyResDto updateReply(Long id, ReplyUpdateDto dto, Long memberId) {
        Reply reply = this.validMember(id, memberId);
        reply.update(dto.getContents()); // 정보 update
        replyRepository.save(reply); // 저장
        return new ReplyResDto(reply);
    }

    @Override
    public void deleteReply(Long id, Long memberId) {
        Reply reply = this.validMember(id, memberId);
        replyRepository.delete(reply);
    }

    private Reply getReply(Long id) {
        return replyRepository.findByIdWithActiveMemberAndActiveSchedule(id)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "댓글 조회 실패: ID " + id + " 에 해당하는 댓글 없음")); // 조회 실패시 throw
    }

    private Reply validMember(Long id, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(CommonExceptionResultMessage.NOT_FOUND, "회원 조회 실패: ID " + memberId + " 에 해당하는 회원 없음")); // 조회 실패시 throw

        Reply reply = this.getReply(id);

        if (!memberId.equals(reply.getMember().getId())) { // 회원 검증
            throw new CustomException(CommonExceptionResultMessage.ACCESS_DENIED);
        }

        return reply;
    }
}
