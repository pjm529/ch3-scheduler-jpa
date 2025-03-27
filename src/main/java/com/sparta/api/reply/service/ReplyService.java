package com.sparta.api.reply.service;

import com.sparta.api.reply.dto.ReplyReqDto;
import com.sparta.api.reply.dto.ReplyResDto;
import com.sparta.api.reply.dto.ReplyUpdateDto;

public interface ReplyService {
    ReplyResDto saveReply(ReplyReqDto dto, Long memberId);

    ReplyResDto findReplyById(Long id);

    ReplyResDto updateReply(Long id, ReplyUpdateDto dto, Long memberId);

    void deleteReply(Long id, Long memberId);
}
