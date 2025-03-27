package com.sparta.common.exception;

import com.sparta.common.component.BaseResponse;
import com.sparta.common.component.CommonExceptionResultMessage;
import com.sparta.common.component.JSONResult;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Slf4j
@ControllerAdvice
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
@Hidden // Swagger 문서에서 제외
public class ExceptionAdvice {

	@ExceptionHandler(CustomException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Object customException(HttpServletRequest request, CustomException e) {
		log.error("Exception URI : " + request.getRequestURI());
		log.error("customException : " + e.getMessage(), e);

		BaseResponse res = new BaseResponse();
		// CustomException에서 정의한 에러 정보와 예외 메시지를 활용
		res.setJsonResult(JSONResult.failure(e.getResultMessage(), e));
		return ResponseEntity.status(e.getResultMessage().getStatus()).body(res);
	}

	@ExceptionHandler({
			HttpClientErrorException.class,
			MissingServletRequestParameterException.class,
			NoResourceFoundException.class
	})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Object httpClientErrorException(HttpServletRequest request, Exception e) {
		log.error("Exception URI : " + request.getRequestURI());
		log.error("NotFound : " + e.getMessage(), e);
		BaseResponse res = new BaseResponse();
		res.setJsonResult(JSONResult.failure(CommonExceptionResultMessage.NOT_FOUND, e));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
	}

	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Object handleDataAccessException(HttpServletRequest request, DataAccessException e) {
		log.error("Exception URI : " + request.getRequestURI());
		log.error("DataAccessException : " + e.getMessage(), e);

		BaseResponse res = new BaseResponse();
		res.setJsonResult(JSONResult.failure(CommonExceptionResultMessage.DB_FAIL, e));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Object handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
		log.error("MethodArgumentNotValidException URI : " + request.getRequestURI());
		log.error("MethodArgumentNotValidException : " + e.getMessage(), e);

		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		FieldError fieldError = fieldErrors.get(fieldErrors.size() - 1);  // 가장 마지막 에러 필드 선택
		String message = fieldError.getDefaultMessage();

		BaseResponse res = new BaseResponse();
		res.setJsonResult(JSONResult.failure(CommonExceptionResultMessage.VALID_FAIL, message));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Object exception(HttpServletRequest request, Exception e) {
		log.error("Exception URI : " + request.getRequestURI());
		log.error("Exception : " + e.getMessage(), e);
		BaseResponse res = new BaseResponse();
		res.setJsonResult(JSONResult.failure(CommonExceptionResultMessage.FAIL, e));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}
}
