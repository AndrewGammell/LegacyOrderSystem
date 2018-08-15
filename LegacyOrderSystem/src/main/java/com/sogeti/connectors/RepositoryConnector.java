package com.sogeti.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sogeti.properties.CustomProperties;

public final class RepositoryConnector {

	private static final Logger logger = Logger.getLogger(RepositoryConnector.class);

	public RepositoryConnector() {
	}

	// The purpose of this method is to control the construction of the
	// RepositoryConnector without making it a singleton.
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(CustomProperties.getProperty("db_driver"));
			con = DriverManager.getConnection(CustomProperties.getProperty("db_connection"),
					CustomProperties.getProperty("db_user"), CustomProperties.getProperty("db_password"));

		} catch (SQLException sqle) {
			logger.error(sqle);
		} catch (ClassNotFoundException cnfe) {
			logger.error(cnfe);
		}
		return con;
	}

}
