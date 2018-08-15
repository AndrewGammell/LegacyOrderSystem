package com.sogeti.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.sogeti.adapter.EntityAdapter;
import com.sogeti.connectors.RepositoryConnector;
import com.sogeti.entitymanager.JPAEntityManager;
import com.sogeti.interfaces.RepositoryInterface;
import com.sogeti.model.UserModel;

public class UsersRepositoryImpl implements RepositoryInterface<UserModel> {

	private static final Logger	logger		= Logger.getLogger(UsersRepositoryImpl.class);
	private Connection			connector	= new RepositoryConnector().getConnection();
	private Statement			statement;
	private ResultSet			results;
	private SessionFactory		factory;
	private Session				session;
	private Gson				gson		= new Gson();

	private String	getUsers					= "SELECT * FROM person";
	private String	getUserById					= "SELECT * FROM person WHERE id=%d";
	private String	getUserByEmailAndPassword	= "SELECT * FROM person WHERE email = '%s' AND password = '%s'";
	private String	deleteFromDatabase			= "DELETE FROM person WHERE id=?";

	// Retrieves all the User objects from the repository
	@Override
	public List<UserModel> getAllObjects(int id) throws SQLException {
		List<UserModel> users = new ArrayList<>();

		statement = connector.createStatement();
		results = statement.executeQuery(getUsers);

		while (results.next()) {
			users.add(EntityAdapter.parseUser(results));
		}

		logger.debug("returning a list of user from the UserImpl getAllObjects method");
		return users;
	}

	// Retrieves a User object from then repository using the int id passed into the
	// method
	@Override
	public UserModel getObjectById(int id) throws SQLException {
		UserModel user = null;
		statement = connector.createStatement();
		results = statement.executeQuery(String.format(getUserById, id));

		results.next();
		user = EntityAdapter.parseUser(results);

		logger.debug("returning a user from the UserImpl getObjectById method");
		return user;
	}

	// The purpose of this method is to check if a user exists in the repository,
	// matching email and password values passed into the method.
	public boolean isValidUser(String username, String password) throws SQLException {

		statement = connector.createStatement();
		results = statement.executeQuery(String.format(getUserByEmailAndPassword, username, password));

		logger.debug("returning a boolean from the UserImpl isValidUser method");
		return results.next();
	}

	// The purpose of this method is to retrieve a User from the repository using
	// the username and password values passed into the method
	public UserModel getUserWithCredentials(String username, String password) throws SQLException {

		UserModel user = null;
		statement = connector.createStatement();
		results = statement.executeQuery(String.format(getUserByEmailAndPassword, username, password));

		results.next();
		user = EntityAdapter.parseUser(results);

		logger.debug("returning a user from the UserImpl getUserWithCredentials method");
		return user;
	}

	// This method uses hibernate to update the object on the DB
	@Override
	@Transactional
	public String updateObject(String body, int id) {

		EntityManager entityManager = JPAEntityManager.getEntityManager();
		entityManager.getTransaction().begin();

		UserModel model = jsonToObject(body);
		UserModel entity = entityManager.find(UserModel.class, id);

		updateEntity(model, entity);

		entityManager.getTransaction().commit();
		entityManager.close();

		return objectToJSON(entity);
	}

	// This method uses hibernate to create the object on the DB
	@Override
	@Transactional
	public String createObject(String body) {

		UserModel user = jsonToObject(body);

		EntityManager entityManager = JPAEntityManager.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();

		return objectToJSON(user);
	}

	// This method deletes an object on the database with the id passed in
	@Override
	public boolean deleteObject(int id) {

		try (PreparedStatement st = connector.prepareStatement(deleteFromDatabase)) {

			st.setInt(1, id);
			st.executeUpdate();

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	// This method converts the json object from the message into a POJO for
	// hibernate operations
	private UserModel jsonToObject(String body) {
		return gson.fromJson(body, UserModel.class);
	}

	private String objectToJSON(UserModel user) {
		return gson.toJson(user);
	}

	private void updateEntity(UserModel model, UserModel entity) {

		if (model.getCreatedDate() != null) {
			entity.setCreatedDate(model.getCreatedDate());
		}

		if (model.getDateOfBirth() != null) {
			entity.setDateOfBirth(model.getDateOfBirth());
		}

		if (!model.getEmail().isEmpty()) {
			entity.setEmail(model.getEmail());
		}

		if (!model.getFirstName().isEmpty()) {
			entity.setFirstName(model.getFirstName());
		}

		if (!model.getLastName().isEmpty()) {
			entity.setLastName(model.getLastName());
		}

		if (model.getId() > 0) {
			entity.setId(model.getId());
		}

		if (!model.getPassword().isEmpty()) {
			entity.setPassword(model.getPassword());
		}

		if (model.getUpdatedDate() != null) {
			entity.setUpdatedDate(model.getUpdatedDate());
		}

	}
}
