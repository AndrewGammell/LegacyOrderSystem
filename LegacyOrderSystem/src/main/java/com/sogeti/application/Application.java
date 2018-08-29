package com.sogeti.application;

import org.apache.log4j.Logger;

import com.sogeti.properties.PropertiesReader;
import com.sogeti.services.QueueService;

public class Application {
	private static final Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] argv) {
		logger.info(PropertiesReader.getProperty("env-greeting"));

		QueueService queue = new QueueService();
		queue.run();
	}

}