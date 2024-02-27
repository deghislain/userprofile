package com.userprofile.service;

import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.userprofile.exception.DuplicateUserProfileException;
import com.userprofile.exception.UserExecutionException;
import com.userprofile.exception.UserProfileSQLException;
import com.userprofile.model.UserProfile;
import com.userprofile.repository.UserProfileRepository;
import com.userprofile.validator.UserProfileValidator;



@Service
public class UserProfileService {
	@Autowired
	private UserProfileRepository upr;
	
	@Autowired
	private UserProfileValidator validator;
	

	LoadingCache<String, UserProfile> userProfileCache = CacheBuilder.newBuilder().maximumSize(1000) // Adjust cache
																										// size
			.expireAfterWrite(10, TimeUnit.MINUTES) // Adjust expiration
			.build(new CacheLoader<String, UserProfile>() {
				@Override
				public UserProfile load(String userId) throws UserProfileSQLException {
					return upr.queryUserProfile(userId);
				}
			});

	public UserProfile fetchUserProfile(String userId) throws UserExecutionException {
		if (userId == null || userId.isEmpty()) {
			throw new InvalidParameterException("Invalid user ID: cannot be null or empty");
		}
		try {
			return userProfileCache.get(userId);
		} catch (ExecutionException e) {
			// Handle exceptions
			throw new UserExecutionException("Error while retrieving user from cache");
		}
	}

	public void addUserProfile(UserProfile up) throws UserExecutionException, UserProfileSQLException {
		if (up == null) {
			throw new InvalidParameterException("UserProfile cannot be null");
		}
		UserProfile userProfFound = fetchUserProfile(up.getUserId());
		if (userProfFound != null && userProfFound.getUserId() != null && !userProfFound.getUserId().isBlank()) {
			throw new DuplicateUserProfileException("UserProfile with ID " + up.getUserId() + " already exists");
		}
		String missingFields = validator.validate(up);
		if(missingFields.isEmpty()) {
			upr.saveUserProfileToDB(up);
		}else {
			throw new InvalidParameterException("Error while creating new user profile. The following fields are missing: " +missingFields);
		}
	}
	
}