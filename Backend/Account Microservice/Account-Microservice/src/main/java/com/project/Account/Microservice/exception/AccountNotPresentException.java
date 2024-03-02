package com.project.Account.Microservice.exception;

public class AccountNotPresentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotPresentException(String msg){
		super(msg);
	}
}
