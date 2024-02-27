package com.userprofile.exception;

//DuplicateEmployeeException.java
public class DuplicateUserProfileException extends RuntimeException { // Or extend Exception if you want it checked
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public DuplicateUserProfileException(String message) {
     super(message);
 }
}