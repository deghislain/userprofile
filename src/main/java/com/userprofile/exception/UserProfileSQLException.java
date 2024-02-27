package com.userprofile.exception;

import java.sql.SQLException;

public class UserProfileSQLException extends SQLException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserProfileSQLException(String message) {
	     super(message);
	 }
}
