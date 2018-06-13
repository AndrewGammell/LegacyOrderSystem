package com.sogeti.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sogeti.adapter.EntityAdapter;
import com.sogeti.application.Application;
import com.sogeti.entity.UserEntity;

public class UsersRepositoryImpl implements RepositoryInterface<UserEntity> {

	private static final Logger logger = Logger.getLogger(UsersRepositoryImpl.class);
	private Connection connector = RepositoryConnector.getConnection();
	private Statement statement;
	private ResultSet results;

	private String getUsers = "SELECT * FROM person";
	private String getUserById = "SELECT * FROM person WHERE id=%d";
	private String getUserByEmailAndPassword = "SELECT * FROM person WHERE email = '%s' AND password = '%s'";

	// Retrieves all the User objects from the repository
	public List<UserEntity> getAllObjects() {
		List<UserEntity> users = new ArrayList<>();

		try {
			statement = connector.createStatement();
			results = statement.executeQuery(getUsers);

			while (results.next()) {
				users.add(EntityAdapter.parseUser(results));
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
		}
		logger.debug("returning a list of user from the UserImpl getAllObjects method");
		return users;
	}

	// Retrieves a User object from then repository using the int id passed into the
	// method
	public UserEntity getObjectById(int id) {
		UserEntity user = null;
		try {
			statement = connector.createStatement();
			results = statement.executeQuery(String.format(getUserById, id));

			results.next();
			user = EntityAdapter.parseUser(results);

			logger.debug("returning a list of user from the UserImpl getAllObjects method");
		} catch (SQLException sqle) {
			logger.error(sqle);
		}

		logger.debug("returning a user from the UserImpl getObjectById method");
		return user;
	}

	// The purpose of this method is to check if a user exists in the repository,
	// matching email and password values passed into the method.
	public boolean isValidUser(String username, String password) {

		boolean isValid;

		try {
			statement = connector.createStatement();
			results = statement.executeQuery(String.format(getUserByEmailAndPassword, username, password));

			isValid = results.next();

		} catch (SQLException sqle) {
			logger.error(sqle);

			return false;
		}

		logger.debug("returning a boolean from the UserImpl isValidUser method");
		return isValid;
	}

	// The purpose of this method is to retrieve a User from the repository using
	// the username and password values passed into the method
	public UserEntity getUserWithCredentials(String username, String password) {
		UserEntity user = null;
		try {
			statement = connector.createStatement();
			results = statement.executeQuery(String.format(getUserByEmailAndPassword, username, password));

			results.next();
			user = EntityAdapter.parseUser(results);

		} catch (SQLException sqle) {
			logger.error(sqle);
		}

		logger.debug("returning a user from the UserImpl getUserWithCredentials method");
		return user;
	}

}
