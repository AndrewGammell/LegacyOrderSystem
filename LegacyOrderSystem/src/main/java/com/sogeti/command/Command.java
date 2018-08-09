package com.sogeti.command;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.sogeti.interfaces.RepositoryInterface;
import com.sogeti.repository.DetailsRepositoryImpl;
import com.sogeti.repository.OrdersRepositoryImpl;
import com.sogeti.repository.UsersRepositoryImpl;

public class Command {

	private Logger				logger	= Logger.getLogger(Command.class);
	private Gson				gson	= new Gson();
	private RepositoryInterface	repo;

	private queryQuantity		quantity;
	private queryType			type;
	private queryTable			table;
	private String				body;
	private Map<String, String>	values;

	public enum queryQuantity {
		SINGLE, MULTIPLE
	}

	public enum queryType {
		GET, PUT, POST, DELETE
	}

	public enum queryTable {
		ORDERS, DETAILS, USERS
	}

	public String executeCommand() throws SQLException {
		logger.debug("executing the command");

		selectRepository();

		return makeQuery();
	}

	// This method selects the correct repository based on the table being queried
	private void selectRepository() {
		logger.debug("selecting repository");

		switch (table) {
			case ORDERS:
				logger.debug("chose Orders repository");
				repo = new OrdersRepositoryImpl();
			break;
			case DETAILS:
				logger.debug("chose Details repository");
				repo = new DetailsRepositoryImpl();
			break;
			case USERS:
				logger.debug("chose Users repository");
				repo = new UsersRepositoryImpl();
			break;
			default:
				throw new IllegalArgumentException("Could not select repository");

		}
	}

	// This method selects a method based on the query type
	private String makeQuery() throws SQLException {
		logger.debug("Chosing action to take");

		switch (type) {
			case GET:
				return getObject();
			case PUT:
				return putObject();
			case POST:
				return postObject();
			case DELETE:
				return deleteObject();
			default:
				return null;

		}
	}

	// This method gets the object/s from the selected repository
	private String getObject() throws SQLException {
		logger.debug("getting objects");
		String response;
		switch (quantity) {
			case SINGLE:
				if (repo instanceof UsersRepositoryImpl) {
					logger.debug("Returning single user");
					response = gson.toJson(((UsersRepositoryImpl) repo).getUserWithCredentials(values.get("email"),
							values.get("password")));
					logger.debug("Reponse from get for single user: " + response);
					return response;
				}
				response = gson.toJson(repo.getObjectById(Integer.valueOf(values.get("id"))));
				logger.debug("Response from get single object: " + response);
				return response;
			case MULTIPLE:
				response = gson.toJson(repo.getAllObjects(Integer.valueOf(values.get("customerId"))));
				logger.debug("Response from get multiple objects: " + response);
				return response;
			default:
				return null;
		}
	}

	// This method updates the object/s from the selected repository
	private String putObject() {
		logger.debug("updating objects");
		String response;
		switch (quantity) {
			case SINGLE:
				response = String.valueOf(repo.updateObject(body, Integer.valueOf(values.get("id"))));
				logger.debug("Response from put single object: " + response);
				return response;
			case MULTIPLE:
				return null;
			default:
				return null;
		}
	}

	// This method creates the object/s from the selected repository
	private String postObject() {
		logger.info("creating object");
		String response;

		switch (quantity) {
			case SINGLE:
				response = String.valueOf(repo.createObject(body));
				logger.debug("Response from post single object: " + response);
				return response;
			case MULTIPLE:
				return null;
			default:
				return null;
		}
	}

	// This method removes the object/s from the selected repository
	private String deleteObject() throws SQLException {
		logger.debug("deleting object");
		String response;

		switch (quantity) {
			case SINGLE:
				if (values.get("id") == null || values.get("id").isEmpty()) {
					return "false";
				}
				response = String.valueOf(repo.deleteObject(Integer.valueOf(values.get("id"))));
				logger.debug("Response from delete single object: " + response);
				return response;
			case MULTIPLE:
				return null;
			default:
				return null;
		}
	}

	public void setQuantity(queryQuantity quantity) {
		this.quantity = quantity;
	}

	public void setType(queryType type) {
		this.type = type;
	}

	public void setTable(queryTable table) {
		this.table = table;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Command other = (Command) obj;
		if (quantity != other.quantity) {
			return false;
		}
		if (table != other.table) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Command [gson=" + gson + ", repo=" + repo + ", quantity=" + quantity + ", type=" + type + ", table="
				+ table + ", value=" + values + ", body=" + body + "]";
	}

}
