package com.sogeti.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sogeti.properties.PropertiesReader;

public final class RepositoryConnector {

	private static final Logger logger = Logger.getLogger(RepositoryConnector.class);

	// The purpose of this method is to control the construction of the
	// RepositoryConnector without making it a singleton.
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(PropertiesReader.getProperty("db_driver"));
			con = DriverManager.getConnection(PropertiesReader.getProperty("db_connection"),
					PropertiesReader.getProperty("db_user"), PropertiesReader.getProperty("db_password"));

		} catch (SQLException sqle) {
			logger.error(sqle);
		} catch (ClassNotFoundException cnfe) {
			logger.error(cnfe);
		}
		return con;
	}

}
