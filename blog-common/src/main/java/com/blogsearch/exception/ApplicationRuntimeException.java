package com.blogsearch.exception;

public class ApplicationRuntimeException extends RuntimeException {

	public ApplicationRuntimeException() {
	}

	public ApplicationRuntimeException(final String message) {
		super(message);
	}
}
