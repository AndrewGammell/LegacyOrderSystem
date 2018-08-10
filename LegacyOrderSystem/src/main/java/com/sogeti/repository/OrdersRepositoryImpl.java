package com.sogeti.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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
import com.sogeti.model.OrderModel;

public class OrdersRepositoryImpl implements RepositoryInterface<OrderModel> {

	private static final Logger logger = Logger.getLogger(OrdersRepositoryImpl.class);

	private Connection		connector	= RepositoryConnector.getConnection();
	private Statement		statement;
	private ResultSet		results;
	private SessionFactory	factory;
	private Session			session;
	private Gson			gson		= new Gson();

	// the %d is used by the string format method to set the value
	private String rightJoinQuery = "SELECT * FROM orders where orders.customerId = %d";

	// the %d is used by the string format method to set the value
	private String rightJoinFindById = "SELECT * FROM orders where orders.orderId = %d";

	// the ? in the string is used by the prepared statement to set the value
	private String deleteFromDatabase = "DELETE FROM orders where orderId=?";

	// Retrieves all Order objects from the repository

	@Override
	public List<OrderModel> getAllObjects(int customerId) throws SQLException {
		List<OrderModel> ordersList = new ArrayList<>();
		statement = connector.createStatement();

		results = statement.executeQuery(String.format(rightJoinQuery, customerId));

		while (results.next()) {
			OrderModel order = EntityAdapter.parseOrder(results);
			ordersList.add(order);
		}

		logger.info("returning order list");
		return ordersList;
	}

	// Retrieves an Order object from the repository using the int id passed into
	// the method
	@Override
	public OrderModel getObjectById(int orderId) throws SQLException {

		OrderModel order = null;
		statement = connector.createStatement();

		results = statement.executeQuery(String.format(rightJoinFindById, orderId));
		logger.debug("orderId passed in was: " + orderId);

		results.next();
		order = EntityAdapter.parseOrder(results);

		logger.debug("returning order retrived by id");

		return order;
	}

	// This method uses hibernate to update the object on the DB
	@Override
	@Transactional
	public String updateObject(String body, int id) {

		String response;

		try {

			EntityManager entityManager = JPAEntityManager.getEntityManager();

			entityManager.getTransaction().begin();

			OrderModel model = jsonToObject(body);
			OrderModel entity = entityManager.find(OrderModel.class, id);

			logger.info("Entity found " + entity);
			updateEntity(model, entity);
			logger.info("Entity being updated " + entity);

			entityManager.merge(entity);
			entityManager.getTransaction().commit();

			response = objectToJSON(entity);
		} catch (Exception e) {
			response = "Exception caught in updateObject() of the order repository casued by " + e.getCause();
		}
		return response;
	}

	// This method uses hibernate to create the object on the DB
	@Override
	@Transactional
	public String createObject(String body) {
		String response;
		try {

			OrderModel order = jsonToObject(body);

			EntityManager entityManager = JPAEntityManager.getEntityManager();
			entityManager.getTransaction().begin();

			entityManager.persist(order);
			entityManager.getTransaction().commit();

			response = objectToJSON(order);
		} catch (Exception e) {

			response = "Exception caught in createObject() of the order repository casued by " + e.getCause();
		}

		return response;
	}

	// This method deletes an object on the database with the id passed in
	@Override
	public boolean deleteObject(int id) {

		try (PreparedStatement st = connector.prepareStatement(deleteFromDatabase)) {

			st.setInt(1, id);
			st.executeUpdate();

		} catch (Exception e) {
			logger.error(e);
			return false;
		}

		return true;
	}

	// This method converts the json object from the message into a POJO for
	// hibernate operations
	private OrderModel jsonToObject(String body) {
		return gson.fromJson(body, OrderModel.class);
	}

	private String objectToJSON(OrderModel obj) {

		return gson.toJson(obj);
	}

	// This method is used to update the entity found on the db with the model
	// passed in through the client
	private void updateEntity(OrderModel model, OrderModel entity) {

		if (model.getCreatedDate() != null) {
			entity.setCreatedDate(model.getCreatedDate());
		}

		if (model.getCreatedStaffId() > 0) {
			entity.setCreatedStaffId(model.getCreatedStaffId());
		}

		if (model.getCustomerId() > 0) {
			entity.setCustomerId(model.getCustomerId());
		}

		if (model.getDateOrdered() != null) {
			entity.setDateOrdered(model.getDateOrdered());
		}

		if (model.getDateReceived() != null) {
			entity.setDateReceived(model.getDateReceived());
		}

		if (model.getStatus() != null) {
			entity.setStatus(model.getStatus());
		}

		entity.setUpdatedDate(new Date());

		if (model.getUpdatedStaffId() > 0) {
			entity.setUpdatedStaffId(model.getUpdatedStaffId());
		}

	}
}
