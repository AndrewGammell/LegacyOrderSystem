package com.sogeti.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

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
	public String updateObject(String body) {

		OrderModel order = jsonToObject(body);

		EntityManager entityManager = JPAEntityManager.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.merge(order);
		entityManager.getTransaction().commit();
		entityManager.close();

		return objectToJSON(order);
	}

	// This method uses hibernate to create the object on the DB
	@Override
	public String createObject(String body) {
		OrderModel order = jsonToObject(body);

		EntityManager entityManager = JPAEntityManager.getEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(order);
		entityManager.getTransaction().commit();
		entityManager.close();

		return objectToJSON(order);
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

}
