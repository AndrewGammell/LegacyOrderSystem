package com.sogeti.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public final class RepositoryConnector {

	private static final Logger logger = Logger.getLogger(RepositoryConnector.class);

	private RepositoryConnector() {
	}

	// The purpose of this method is to control the construction of the
	// RepositoryConnector without making it a singleton.
	// TODO REMOVE STATIC KEYWORD TO USE PROPERTY FILE
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://sogetiirelanddigitalin.mysql.database.azure.com:3306/legacy?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=DigInt2018@sogetiirelanddigitalin&password=D1gIt@lInT!");
		} catch (SQLException sqle) {
			logger.error(sqle);
		} catch (ClassNotFoundException cnfe) {
			logger.error(cnfe);
		}
		return con;
	}

}
