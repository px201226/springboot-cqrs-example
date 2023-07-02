package com.blogsearch.exception;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

	private String errorType;
	private String message;

	public static ErrorResponse from(Exception e) {
		return new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
	}

	public static ErrorResponse from(Exception e, String message) {
		return new ErrorResponse(e.getClass().getSimpleName(), message);
	}
}
