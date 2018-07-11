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

		order.setOrderId(results.getInt("order_id"));
		order.setCreatedDate(results.getDate("created_date"));
		order.setCreatedStaffId(results.getString("created_staff_id"));
		order.setDateOrdered(results.getDate("date_ordered"));
		order.setDateReceived(results.getDate("date_received"));
		order.setUpdatedDate(results.getDate("updated_date"));
		order.setUpdatedStaffId(results.getString("updated_staff_id"));

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

		details.setCreatedDate(results.getDate("created_date"));
		details.setCreatedStaffId(results.getString("created_staff_id"));
		details.setQuantity(results.getInt("quantity"));
		details.setOrderId(results.getInt("order_id"));
		details.setProductId(results.getInt("product_id"));
		details.setUnitPrice(results.getInt("unit_price"));
		details.setUpdatedDate(results.getDate("updated_date"));
		details.setUpdatedStaffId(results.getString("updated_staff_id"));

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
