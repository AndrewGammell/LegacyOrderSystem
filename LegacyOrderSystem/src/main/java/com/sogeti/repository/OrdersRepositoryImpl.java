package com.sogeti.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.sogeti.adapter.EntityAdapter;
import com.sogeti.connectors.RepositoryConnector;
import com.sogeti.hibernate.SessionFactoryBuilder;
import com.sogeti.interfaces.RepositoryInterface;
import com.sogeti.model.OrderModel;

public class OrdersRepositoryImpl implements RepositoryInterface<OrderModel> {

	private static final Logger logger = Logger.getLogger(OrdersRepositoryImpl.class);
	private Connection connector = RepositoryConnector.getConnection();
	private Statement statement;
	private ResultSet results;
	private SessionFactory factory;
	private Session session;
	private Gson gson = new Gson();

	private String rightJoinQuery = "SELECT * FROM orders RIGHT OUTER JOIN orders_details ON"
			+ " orders.order_id = orders_details.order_id";

	private String rightJoinFindById = "SELECT * FROM orders RIGHT OUTER JOIN orders_details ON orders.order_id"
			+ " = orders_details.order_id WHERE orders.order_id=%d";

	// Retrieves all Order objects from the repository
	public List<OrderModel> getAllObjects() throws SQLException {
		List<OrderModel> ordersList = new ArrayList<>();
		statement = connector.createStatement();

		results = statement.executeQuery(rightJoinQuery);

		while (results.next()) {
			OrderModel order = EntityAdapter.parseOrder(results);
			ordersList.add(order);
		}

		logger.info("returning order list");
		return ordersList;
	}

	// Retrieves an Order object from the repository using the int id passed into
	// the method
	public OrderModel getObjectById(int orderId) throws SQLException {

		OrderModel order = null;
		statement = connector.createStatement();

		results = statement.executeQuery(String.format(rightJoinFindById, orderId));
		results.next();

		order = EntityAdapter.parseOrder(results);

		logger.info("returning order retrived by id");
		return order;
	}

	// This method uses hibernate to update the object on the DB
	@Override
	public boolean updateObject(String body) {

		try {
			factory = SessionFactoryBuilder.getSessionFactory();
			session = factory.getCurrentSession();
			session.beginTransaction();
			session.update(convertBody(body));
			session.getTransaction().commit();
			
		} catch (Exception e) {
			logger.error(e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return false;
		}

		return true;
	}

	// This method uses hibernate to create the object on the DB
	@Override
	public boolean createObject(String body) {

		try {
			factory = SessionFactoryBuilder.getSessionFactory();
			session = factory.getCurrentSession();
			session.beginTransaction();
			session.save(convertBody(body));
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error(e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return false;
		}

		return true;
	}

	// This method converts the json object from the message into a POJO for
	// hibernate operations
	private OrderModel convertBody(String body) {
		return gson.fromJson(body, OrderModel.class);
	}

}
