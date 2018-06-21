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

	private Logger logger = Logger.getLogger(Command.class);
	private Gson gson = new Gson();
	private RepositoryInterface repo;

	private queryQuantity quantity;
	private queryType type;
	private queryTable table;
	private String body;
	private Map<String, String> values;

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

		switch (table) {
		case ORDERS:
			repo = new OrdersRepositoryImpl();
			break;
		case DETAILS:
			repo = new DetailsRepositoryImpl();
			break;
		case USERS:
			repo = new UsersRepositoryImpl();
			break;
		default:
			repo = null;

		}
	}

	// This method selects a method based on the query type
	private String makeQuery() throws SQLException {

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

		switch (quantity) {
		case SINGLE:
			if (repo instanceof UsersRepositoryImpl) {
				logger.debug("Returning single user");
				return gson.toJson(((UsersRepositoryImpl) repo).getUserWithCredentials(values.get("email"),
						values.get("password")));
			}
			return gson.toJson(repo.getObjectById(Integer.valueOf(values.get("id"))));
		case MULTIPLE:
			return gson.toJson(repo.getAllObjects());
		default:
			return null;
		}
	}

	// This method updates the object/s from the selected repository
	private String putObject() {

		switch (quantity) {
		case SINGLE:
			return String.valueOf(repo.updateObject(body));
		case MULTIPLE:
			return null;
		default:
			return null;
		}
	}

	// This method creates the object/s from the selected repository
	private String postObject() {
	logger.info("creating object on DB");

		switch (quantity) {
		case SINGLE:
			return String.valueOf(repo.createObject(body));
		case MULTIPLE:
			return null;
		default:
			return null;
		}
	}

	// This method removes the object/s from the selected repository
	private String deleteObject() {

		switch (quantity) {
		case SINGLE:
			return null;
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
