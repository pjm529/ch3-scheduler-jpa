package com.sparta.common.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.examples.Example;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JSONResult {

	@Schema(hidden = true)
	@JsonIgnore
	private Example holder;

	@Schema(description = "HTTP 상태 코드", example = "200")
	private int status;

	@Schema(description = "응답 코드", example = "A000")
	private String code;

	@Schema(description = "응답 메시지", example = "요청 처리 성공")
	private String message;

	@Schema(description = "시간", example = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;

	public static JSONResult success() {
		return JSONResult.builder()
				.status(CommonExceptionResultMessage.SUCCESS.getStatus().value())
				.code(CommonExceptionResultMessage.SUCCESS.getCode())
				.message(CommonExceptionResultMessage.SUCCESS.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
	}

	/**
	 * 기본 실패 응답: 전달받은 에러 enum을 그대로 사용
	 */
	public static JSONResult failure(CommonExceptionResultMessage error) {
		return JSONResult.builder()
				.status(error.getStatus().value())
				.code(error.getCode())
				.message(error.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
	}

	/**
	 * 예외 객체의 메시지를 활용하여 실패 응답 생성.
	 * 예외 메시지가 있으면 해당 메시지를, 없으면 기본 메시지를 사용.
	 */
	public static JSONResult failure(CommonExceptionResultMessage error, Exception ex) {
		String message = (ex != null && StringUtils.isNotBlank(ex.getMessage()))
				? ex.getMessage() : error.getMessage();
		return JSONResult.builder()
				.status(error.getStatus().value())
				.code(error.getCode())
				.message(message)
				.timestamp(LocalDateTime.now())
				.build();
	}

	/**
	 * 사용자 정의 메시지를 전달하여 실패 응답 생성.
	 */
	public static JSONResult failure(CommonExceptionResultMessage error, String customMessage) {
		String message = StringUtils.isNotBlank(customMessage) ? customMessage : error.getMessage();
		return JSONResult.builder()
				.status(error.getStatus().value())
				.code(error.getCode())
				.message(message)
				.timestamp(LocalDateTime.now())
				.build();
	}
}
