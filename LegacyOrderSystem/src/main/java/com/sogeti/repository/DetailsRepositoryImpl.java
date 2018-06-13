package com.sogeti.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sogeti.adapter.EntityAdapter;
import com.sogeti.entity.DetailEntity;

public class DetailsRepositoryImpl implements RepositoryInterface<DetailEntity> {

	private static final Logger logger = Logger.getLogger(DetailsRepositoryImpl.class);
	private Connection connector = RepositoryConnector.getConnection();
	private Statement statement;
	private ResultSet results;

	private String getAllDetails = "SELECT * FROM orders_details";
	private String getDetailsById = "SELECT * FROM orders_details WHERE order_id=%d";

	// Retrieves all OrderDetails objects from repository
	@Override
	public List<DetailEntity> getAllObjects() {

		List<DetailEntity> list = new ArrayList<>();

		try {
			statement = connector.createStatement();
			results = statement.executeQuery(getAllDetails);

			while (results.next()) {
				list.add(EntityAdapter.parseDetails(results));
			}

		} catch (SQLException sqle) {
			logger.error(sqle);
		}

		logger.debug("returning a list of OrderDetails from the DetailsImpl getAllObjects method");
		return list;
	}

	// Retrieves an OrderDetail object from the repository using the int id that is
	// passed into the method
	@Override
	public DetailEntity getObjectById(int id) {
		DetailEntity details = null;
		try {
			statement = connector.createStatement();
			results = statement.executeQuery(String.format(getDetailsById, id));

			results.next();
			details = EntityAdapter.parseDetails(results);

		} catch (SQLException e) {
			logger.error(e);
		}
		logger.debug("returning a OrderDetail from the DetailImpl getObjectById method");

		return details;
	}

}
