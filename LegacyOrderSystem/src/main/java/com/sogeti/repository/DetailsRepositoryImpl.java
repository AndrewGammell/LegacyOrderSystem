package com.sogeti.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.gson.Gson;
import com.sogeti.adapter.EntityAdapter;
import com.sogeti.connectors.RepositoryConnector;
import com.sogeti.hibernate.SessionFactoryBuilder;
import com.sogeti.interfaces.RepositoryInterface;
import com.sogeti.model.DetailModel;

public class DetailsRepositoryImpl implements RepositoryInterface<DetailModel> {

	private static final Logger logger = Logger.getLogger(DetailsRepositoryImpl.class);

	private Connection		connector	= RepositoryConnector.getConnection();
	private Statement		statement;
	private ResultSet		results;
	private SessionFactory	factory;
	private Session			session;
	private Gson			gson		= new Gson();

	private String	getAllDetails		= "SELECT * FROM orders_details WHERE customerId=%d";
	private String	getDetailsById		= "SELECT * FROM orders_details WHERE orderId=%d";
	private String	deleteFromDatabase	= "DELETE FROM orders_details WHERE orderId=?";

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
	@Transactional
	@Override
	public boolean updateObject(String body) {

		try {
			factory = SessionFactoryBuilder.getSessionFactory();
			session = factory.getCurrentSession();
			session.beginTransaction();
			session.update(convertBody(body));
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.debug(e);
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			return false;
		}
		return true;
	}

	// This method uses hibernate to create the object on the DB
	@Transactional
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
	private DetailModel convertBody(String body) {
		return gson.fromJson(body, DetailModel.class);
	}

}
