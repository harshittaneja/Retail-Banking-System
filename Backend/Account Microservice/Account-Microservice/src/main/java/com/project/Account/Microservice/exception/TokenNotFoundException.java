package com.project.Account.Microservice.exception;

public class TokenNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenNotFoundException(String msg) {
		super(msg);
	}
}