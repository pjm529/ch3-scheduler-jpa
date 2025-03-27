package com.sparta.common.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.common.exception.CustomException;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.examples.Example;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;

import static com.sparta.common.component.CommonExceptionResultMessage.*;
import static com.sparta.common.component.CommonExceptionResultMessage.VALID_FAIL;


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

	public static JSONResult successBuilder() {
		return JSONResult.builder()
				.status(SUCCESS.getStatus().value())
				.code(SUCCESS.getCode())
				.message(SUCCESS.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
	}

	public static JSONResult failBuilder(Exception e) {
		return JSONResult.builder()
				.status(FAIL.getStatus().value())
				.code(FAIL.getCode())
				.message(FAIL.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
	}

    public static JSONResult failBuilder(CustomException e, String message) {
        return JSONResult.builder()
            .status(e.getResultMessage().getStatus().value())
            .code(e.getResultMessage().getCode())
            .message(message)
			.timestamp(LocalDateTime.now())
            .build();
    }


	public static JSONResult dbFailBuilder(DataAccessException e) {
		return JSONResult.builder()
				.status(DB_FAIL.getStatus().value())
				.code(DB_FAIL.getCode())
				.message(DB_FAIL.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
	}

	public static JSONResult notFoundBuilder(Exception e) {
		return JSONResult.builder()
				.status(NOT_FOUND.getStatus().value())
				.code(NOT_FOUND.getCode())
				.message(NOT_FOUND.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
	}

	public static JSONResult validFailBuilder(MethodArgumentNotValidException e, String message) {
		return JSONResult.builder()
				.status(VALID_FAIL.getStatus().value())
				.code(VALID_FAIL.getCode())
				.message(message)
				.timestamp(LocalDateTime.now())
				.build();
	}

	public static JSONResult unAuthentication() {
		return JSONResult.builder()
				.status(AUTHENTICATION_FAILED.getStatus().value())
				.code(AUTHENTICATION_FAILED.getCode())
				.message(AUTHENTICATION_FAILED.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
	}
}
