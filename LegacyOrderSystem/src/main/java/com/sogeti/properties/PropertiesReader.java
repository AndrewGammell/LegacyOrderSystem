package com.sogeti.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesReader {

	private static final Logger	logger	= Logger.getLogger(PropertiesReader.class);
	private static Properties	prop	= null;

	// This class is private to stop creation of the class and prevent multiple
	// instances of the properties being load and existing in memory.
	private PropertiesReader() {

	}

	/**
	 * This method first instantiates the properties if the properties are null and
	 * then return the property requested if it exists in the config.properties
	 * file.
	 */
	public static String getProperty(String property) {

		if (prop == null) {
			synchronized (PropertiesReader.class) {
				if (prop == null) {
					loadProperties();
				}
			}
		}

		return prop.getProperty(property);
	}

	/*
	 * This method instantiates the properties class and loads in the custom
	 * properties from the config.properties file.
	 */
	private static void loadProperties() {
		prop = new Properties();
		InputStream input;

		try {

			if (System.getenv("env") != null) {
				input = PropertiesReader.class.getClassLoader()
						.getResourceAsStream(System.getenv("env") + ".properties");
			} else {
				input = PropertiesReader.class.getClassLoader().getResourceAsStream("default.properties");
			}

			if (input == null) {
				logger.debug("Properties file could not be found");
			}

			prop.load(input);

		} catch (IOException ioe) {
			logger.error(ioe);
		}
	}

}
