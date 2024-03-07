package com.userprofile.repository;

import java.io.FileInputStream;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.userprofile.controller.UserProfileControler;
import com.userprofile.exception.UserProfileSQLException;
import com.userprofile.model.UserProfile;
import com.userprofile.validator.UserProfileValidator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class UserProfileRepository {
	private static Logger log = LoggerFactory.getLogger(UserProfileControler.class);
	
	private HikariDataSource dataSource;

	@Autowired
	private UserProfileValidator validator;

	public UserProfileRepository() {
		try {
			HikariConfig config = new HikariConfig();
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "application.properties";
			Properties appProps = new Properties();
			appProps.load(new FileInputStream(appConfigPath));
			config.setJdbcUrl(appProps.getProperty("jdbcUrl"));
			config.setUsername(appProps.getProperty("dbUsername"));
			config.setPassword(appProps.getProperty("dbPassword"));
			config.setDriverClassName(org.postgresql.Driver.class.getName());
			config.setMaximumPoolSize(10); // Adjust pool size as needed
			dataSource = new HikariDataSource(config);
		} catch (Exception e) {
			System.out.println("Error: Unable to open the application properties file");
		}
	}

	public UserProfile queryUserProfile(String userId) throws UserProfileSQLException {
		if (userId == null || userId.isEmpty()) {
			throw new InvalidParameterException("Invalid user ID: cannot be null or empty");
		}
		return this.retrieveUserProfileById(userId);
	}

	private UserProfile retrieveUserProfileById(String userId) throws UserProfileSQLException {
		UserProfile up = new UserProfile();
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_profiles WHERE user_id = ?");
			statement.setString(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				up.setUserId(resultSet.getString("user_id"));
				up.setUsername(resultSet.getString("username"));
				up.setFirstname(resultSet.getString("firstname"));
				up.setLastname(resultSet.getString("lastname"));
				up.setEmail(resultSet.getString("email"));
				up.setPhone(resultSet.getString("phone"));
			}
			
			return up;
		} catch (SQLException e) {
			// Handle exceptions
				throw new UserProfileSQLException("Error while connecting to the database");
		}
	}

	public void saveUserProfileToDB(UserProfile up) throws UserProfileSQLException {
		if (up == null) {
			throw new InvalidParameterException("UserProfile cannot be null");
		} else if (!validator.validate(up).isEmpty()) {
			throw new InvalidParameterException(
					"Error while persisting new user profile. Make sure all the required are provided");
		}
		this.saveUser(up);
	}

	private void saveUser(UserProfile up) throws UserProfileSQLException {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO user_profiles(user_id, username, firstname, lastname, email, phone, hash_password)VALUES(?, ?, ?, ?, ?, ?, ?)");
			statement.setString(1, up.getUserId());
			statement.setString(2, up.getUsername());
			statement.setString(3, up.getFirstname());
			statement.setString(4, up.getLastname());
			statement.setString(5, up.getEmail());
			statement.setString(6, up.getPhone());
			statement.setString(7, up.getPassword());

			statement.executeUpdate();
		} catch (SQLException e) {
			// Handle exceptions
			System.err.format("******************SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			log.error("Error: Unable to store user {}", e);
			throw new UserProfileSQLException("Error while persisting a new user");
		}
	}
}
