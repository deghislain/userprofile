package com.userprofile.exception;

import java.util.concurrent.ExecutionException;

public class UserExecutionException extends ExecutionException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserExecutionException(String message) {
	     super(message);
	 }
}
