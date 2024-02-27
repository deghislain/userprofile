package com.userprofile.controller;

import java.security.InvalidParameterException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userprofile.exception.UserExecutionException;
import com.userprofile.exception.UserProfileSQLException;
import com.userprofile.exception.UserProfleNotFoundException;
import com.userprofile.model.ErrorDetails;
import com.userprofile.model.UserProfile;
import com.userprofile.service.UserProfileService;
import com.userprofile.utility.ProblemDetailBuilder;
import com.userprofile.utility.UniqueIdGenerator;
import com.userprofile.validator.UserProfileValidator;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserProfileControler {

	// @Autowired
	// private PasswordEncoder passwordEncoder;

	@Autowired
	UserProfileService ups;

	@Autowired
	private UserProfileValidator validator;

	private static Logger log = LoggerFactory.getLogger(UserProfileControler.class);
	private static String NOT_FOUND_URL = "http://localhost/api/v1/userprofile/errors/not-found";
	private static String ERROR_TITLE = "Resource Not Found";
	private static String ERROR_DESCRIPTION = "Invalid user ID: cannot be null or empty";
	private static String HOSTNAME = "localhost";

	@GetMapping("/user/{userId}")
	public UserProfile fetchUserProfileByUserId(@PathVariable("userId") String userId) {
		UserProfile up = new UserProfile();
		log.info("fetchUserProfileByUserId START");
		if (userId == null || userId.isBlank()) {
			InvalidParameterException ipe = new InvalidParameterException("Invalid user ID: cannot be null or empty");
			thowNotFoundException(userId, ipe);
		}
		try {
			up = ups.fetchUserProfile(userId);

		} catch (UserProfleNotFoundException e) {
			thowNotFoundException(userId.toString(), e);
		} catch (UserExecutionException e) {
			thowSystemException(userId, e);
		}

		return up;
	}

	@PostMapping("/user")
	public void addUserProfile(@RequestBody UserProfile up) {
		if (up == null) {
			throw new InvalidParameterException("UserProfile cannot be null");
		}
		UUID userId = UniqueIdGenerator.generate();
		up.setUserId(userId.toString());
		String missingFields = validator.validate(up);
		if (missingFields.isEmpty()) {

			try {
				ups.addUserProfile(up);
			} catch (UserExecutionException | UserProfileSQLException e) {
				thowSystemException(userId.toString(), e);
			}
		} else {
			InvalidParameterException ipe = new InvalidParameterException("Invalid user ID: cannot be null or empty");
			thowNotFoundException(userId.toString(), ipe);
		}
	}

	private void thowNotFoundException(String userId, Exception ex) {
		ErrorDetails err = new ErrorDetails(HttpStatus.NOT_FOUND, NOT_FOUND_URL, ERROR_TITLE, ERROR_DESCRIPTION, userId,
				HOSTNAME);
		ProblemDetailBuilder pdb = new ProblemDetailBuilder(err);
		ProblemDetail pd = pdb.getProbemDetail();
		throw new ErrorResponseException(HttpStatus.NOT_FOUND, pd, ex);
	}

	private void thowSystemException(String userId, Exception ex) {
		String errorUrl = "http://localhost/api/v1/userprofile/errors/system_error";
		ErrorDetails err = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, errorUrl, "System Error", "Unable to complete this request",
				userId, HOSTNAME);
		ProblemDetailBuilder pdb = new ProblemDetailBuilder(err);
		ProblemDetail pd = pdb.getProbemDetail();
		throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, pd, ex);
	}

}
