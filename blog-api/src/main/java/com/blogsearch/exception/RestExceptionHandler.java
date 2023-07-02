package com.blogsearch.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	/**
	 * 클라이언트 파라미터 오류 (잘못된 요청)
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
		log.error("{}", e);

		StringBuilder str = new StringBuilder();
		e.getBindingResult().getAllErrors().stream()
				.map(FieldError.class::cast)
				.forEach(error -> str.append(String.format("%s is %s, ", error.getField(), error.getDefaultMessage())));

		str.deleteCharAt(str.length() - 2);

		return ErrorResponse.from(e, str.toString());
	}

	/**
	 * 핸들링된 서버 런타임 에러
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ApplicationRuntimeException.class})
	public ErrorResponse handleRuntimeException(final ApplicationRuntimeException e) {
		log.error("{}", e);

		return ErrorResponse.from(e);
	}

	/**
	 * 서버 에러 (알수없는 에러)
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({RuntimeException.class})
	public ErrorResponse handleRuntimeException(final RuntimeException e) {
		log.error("{}", e);

		return ErrorResponse.from(e);
	}

}
