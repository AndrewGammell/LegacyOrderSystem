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

import com.google.gson.Gson;
import com.sogeti.adapter.EntityAdapter;
import com.sogeti.connectors.RepositoryConnector;
import com.sogeti.entitymanager.JPAEntityManager;
import com.sogeti.interfaces.RepositoryInterface;
import com.sogeti.model.DetailModel;

public class DetailsRepositoryImpl implements RepositoryInterface<DetailModel> {

	private static final Logger logger = Logger.getLogger(DetailsRepositoryImpl.class);

	private Connection	connector	= new RepositoryConnector().getConnection();
	private Statement	statement;
	private ResultSet	results;
	private Gson		gson		= new Gson();

	private String	getAllDetails		= "SELECT * FROM ordersdetails WHERE customerId=%d";
	private String	getDetailsById		= "SELECT * FROM ordersdetails WHERE orderId=%d";
	private String	deleteFromDatabase	= "DELETE FROM ordersdetails WHERE orderId=?";

	// Retrieves all OrderDetails objects from repository
	@Override
	public List<DetailModel> getAllObjects(int customerId) throws SQLException {

		List<DetailModel> list = new ArrayList<>();

		statement = connector.createStatement();
		results = statement.executeQuery(String.format(getAllDetails, customerId));

		while (results.next()) {
			list.add(EntityAdapter.parseDetails(results));
		}
		logger.debug("returning a list of OrderDetails from the DetailsImpl getAllObjects method");
		return list;
	}

	// Retrieves an OrderDetail object from the repository using the int id that is
	// passed into the method
	@Override
	public DetailModel getObjectById(int id) throws SQLException {

		DetailModel details = null;
		statement = connector.createStatement();
		results = statement.executeQuery(String.format(getDetailsById, id));

		results.next();
		details = EntityAdapter.parseDetails(results);

		logger.debug("returning a OrderDetail from the DetailImpl getObjectById method");

		return details;
	}

	// This method uses hibernate to update the object on the DB
	@Override
	@Transactional
	public String updateObject(String body, int id) {

		String response;

		try {
			EntityManager entityManager = JPAEntityManager.getEntityManager();
			entityManager.getTransaction().begin();

			DetailModel model = jsonToObject(body);
			DetailModel entity = entityManager.find(DetailModel.class, id);

			updateEntity(model, entity);

			entityManager.getTransaction().commit();
			entityManager.close();

			response = objectToJSON(entity);
		} catch (Exception e) {
			response = "Exception caught in updateObject() of the details repository casued by " + e.getCause();
		}
		return response;
	}

	// This method uses hibernate to create the object on the DB
	@Override
	@Transactional
	public String createObject(String body) {

		String response;

		try {
			DetailModel detail = jsonToObject(body);

			EntityManager entityManager = JPAEntityManager.getEntityManager();
			entityManager.getTransaction().begin();

			entityManager.persist(detail);
			entityManager.getTransaction().commit();
			entityManager.close();

			response = objectToJSON(detail);
		} catch (Exception e) {
			response = "Exception caught in createObject() of the details repository casued by " + e.getCause();
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
			return false;
		}

		return true;
	}

	// This method converts the json object from the message into a POJO for
	// hibernate operations
	private DetailModel jsonToObject(String body) {
		return gson.fromJson(body, DetailModel.class);
	}

	private String objectToJSON(DetailModel detail) {
		return gson.toJson(detail);
	}

	// This method is used to update the entity found on the db with the model
	// passed in through the client
	private void updateEntity(DetailModel model, DetailModel entity) {

		if (model.getCreatedDate() != null) {
			entity.setCreatedDate(model.getCreatedDate());
		}

		if (model.getCreatedStaffId() > 0) {
			entity.setCreatedStaffId(model.getCreatedStaffId());
		}

		if (model.getCustomerId() > 0) {
			entity.setCustomerId(model.getCustomerId());
		}

		if (model.getProductId() > 0) {
			entity.setProductId(model.getProductId());
		}

		if (model.getQuantity() > 0) {
			entity.setQuantity(model.getQuantity());
		}

		if (model.getUnitPrice() > 0) {
			entity.setUnitPrice(model.getUnitPrice());
		}

		entity.setUpdatedDate(new Date());

		if (model.getUpdatedStaffId() > 0) {
			entity.setUpdatedStaffId(model.getUpdatedStaffId());
		}

	}
}
