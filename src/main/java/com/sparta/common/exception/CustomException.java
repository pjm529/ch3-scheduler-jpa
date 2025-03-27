package com.sparta.common.exception;

import com.sparta.common.component.CommonExceptionResultMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final CommonExceptionResultMessage resultMessage;

    public CustomException(CommonExceptionResultMessage resultMessage) {
        this.resultMessage = resultMessage;
    }

    public CustomException(CommonExceptionResultMessage resultMessage, String message) {
        super(message);
        this.resultMessage = resultMessage;
    }

    public CustomException(CommonExceptionResultMessage resultMessage, String message, Throwable cause) {
        super(message, cause);
        this.resultMessage = resultMessage;
    }

    public CustomException(CommonExceptionResultMessage resultMessage, Throwable cause) {
        super(cause);
        this.resultMessage = resultMessage;
    }

    public CustomException(CommonExceptionResultMessage resultMessage, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultMessage = resultMessage;
    }
}
