package com.sogeti.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryInterface<T> {

	List<T> getAllObjects() throws SQLException;

	T getObjectById(int id) throws SQLException;

	boolean updateObject(String body);
	
	public boolean createObject(String body);

}