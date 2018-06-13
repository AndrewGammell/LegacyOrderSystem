package com.sogeti.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sogeti.entity.OrderEntity;
import com.sogeti.entity.DetailEntity;
import com.sogeti.entity.UserEntity;

public class EntityAdapter {
	// TODO REPLACE WITH HIBERNATE
	private EntityAdapter() {
		
	}

	public static OrderEntity parseOrder(ResultSet results) throws SQLException {

		OrderEntity order = new OrderEntity();	
		
		order.setOrderId(results.getInt("order_id"));
		order.setCreatedDate(results.getDate("created_date"));
		order.setCreatedStaffId(results.getString("created_staff_id"));
		order.setDateOrdered(results.getDate("date_ordered"));
		order.setDateRecieved(results.getDate("date_recieved"));
		order.setStatus(results.getString("status"));
		order.setUpdatedDate(results.getDate("updated_date"));
		order.setUpdatedStaffId(results.getString("updated_staff_id"));		

		return order;
	}

	public static DetailEntity parseDetails(ResultSet results) throws SQLException {

		DetailEntity details = new DetailEntity();

		details.setCreatedDate(results.getDate("created_date"));
		details.setCreateStaffId(results.getString("created_staff_id"));
		details.setQuantity(results.getInt("quantity"));
		details.setOrderId(results.getInt("order_id"));
		details.setProductId(results.getInt("product_id"));
		details.setUnitPrice(results.getInt("unit_price"));
		details.setUpdatedDate(results.getDate("updated_date"));
		details.setUpdatedStaffId(results.getString("updated_staff_id"));

		return details;
	}
	
	public static UserEntity parseUser(ResultSet results) throws SQLException {

		UserEntity user = new UserEntity();	

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
