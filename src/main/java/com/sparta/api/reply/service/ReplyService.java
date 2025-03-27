package com.sparta.api.reply.service;

import com.sparta.api.reply.dto.ReplyReqDto;
import com.sparta.api.reply.dto.ReplyResDto;

public interface ReplyService {
    ReplyResDto saveReply(ReplyReqDto dto, Long memberId);

    ReplyResDto findReplyById(Long id);
}
