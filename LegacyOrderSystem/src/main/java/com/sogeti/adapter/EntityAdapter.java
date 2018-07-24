package com.sogeti.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sogeti.model.DetailModel;
import com.sogeti.model.OrderModel;
import com.sogeti.model.UserModel;

public class EntityAdapter {

	private EntityAdapter() {
	}

	public static OrderModel parseOrder(ResultSet results) throws SQLException {

		OrderModel order = new OrderModel();

		order.setOrderId(results.getInt("orderId"));
		order.setCreatedDate(results.getDate("createdDate"));
		order.setCreatedStaffId(results.getString("createdStaffId"));
		order.setDateOrdered(results.getDate("dateOrdered"));
		order.setDateReceived(results.getDate("dateReceived"));
		order.setUpdatedDate(results.getDate("updatedDate"));
		order.setUpdatedStaffId(results.getString("updatedStaffId"));
		order.setCustomerId(results.getInt("customerId"));

		switch (results.getString("status").toLowerCase()) {
			case "shipped":
				order.setStatus(OrderModel.Status.SHIPPED);
			break;
			case "received":
				order.setStatus(OrderModel.Status.RECEIVED);
			break;
			case "cancelled":
				order.setStatus(OrderModel.Status.CANCELLED);
			break;
		}

		return order;
	}

	public static DetailModel parseDetails(ResultSet results) throws SQLException {

		DetailModel details = new DetailModel();

		details.setCreatedDate(results.getDate("createdDate"));
		details.setCreatedStaffId(results.getString("createdStaffId"));
		details.setQuantity(results.getInt("quantity"));
		details.setOrderId(results.getInt("orderId"));
		details.setProductId(results.getInt("productId"));
		details.setUnitPrice(results.getInt("unitPrice"));
		details.setUpdatedDate(results.getDate("updatedDate"));
		details.setUpdatedStaffId(results.getString("updatedStaffId"));
		details.setCustomerId(results.getInt("customerId"));

		return details;
	}

	public static UserModel parseUser(ResultSet results) throws SQLException {

		UserModel user = new UserModel();

		user.setId(results.getInt("id"));
		user.setFirstName(results.getString("firstname"));
		user.setLastName(results.getString("lastname"));
		user.setEmail(results.getString("email"));
		user.setPassword(results.getString("password"));
		user.setCreatedDate(results.getDate("lastCreated"));
		user.setDateOfBirth(results.getDate("dob"));
		user.setUpdatedDate(results.getDate("lastUpdated"));

		return user;
	}

}
