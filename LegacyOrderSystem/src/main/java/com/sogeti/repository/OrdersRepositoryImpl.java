package com.sogeti.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sogeti.adapter.EntityAdapter;
import com.sogeti.entity.OrderEntity;

public class OrdersRepositoryImpl implements RepositoryInterface<OrderEntity> {

	private static final Logger logger = Logger.getLogger(OrdersRepositoryImpl.class);
	private Connection connector = RepositoryConnector.getConnection();
	private Statement statement;
	private ResultSet results;

	private String rightJoinQuery = "SELECT * FROM orders RIGHT OUTER JOIN orders_details ON"
			+ " orders.order_id = orders_details.order_id";

	private String rightJoinFindById = "SELECT * FROM orders RIGHT OUTER JOIN orders_details ON orders.order_id"
			+ " = orders_details.order_id WHERE orders.order_id=%d";

	// Retrieves all Order objects from the repository
	public List<OrderEntity> getAllObjects() {
		List<OrderEntity> ordersList = new ArrayList<>();
		try {
			statement = connector.createStatement();

			results = statement.executeQuery(rightJoinQuery);

			while (results.next()) {
				OrderEntity order = EntityAdapter.parseOrder(results);
				order.setDetails(EntityAdapter.parseDetails(results));
				ordersList.add(order);
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
		}

		logger.info("returning order list");
		return ordersList;
	}

	// Retrieves an Order object from the repository using the int id passed into
	// the method
	public OrderEntity getObjectById(int orderId) {
		OrderEntity order = null;
		try {
			statement = connector.createStatement();

			results = statement.executeQuery(String.format(rightJoinFindById, orderId));
			results.next();

			order = EntityAdapter.parseOrder(results);
			order.setDetails(EntityAdapter.parseDetails(results));

		} catch (SQLException sqle) {
			logger.error(sqle);
		}

		logger.info("returning order retrived by id");
		return order;
	}

}
