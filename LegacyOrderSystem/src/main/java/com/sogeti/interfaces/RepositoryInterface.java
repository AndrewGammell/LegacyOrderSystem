package com.sogeti.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryInterface<T> {

	List<T> getAllObjects(int customerId) throws SQLException;

	T getObjectById(int id) throws SQLException;

	String updateObject(String body);

	String createObject(String body);

	boolean deleteObject(int id) throws SQLException;
}
