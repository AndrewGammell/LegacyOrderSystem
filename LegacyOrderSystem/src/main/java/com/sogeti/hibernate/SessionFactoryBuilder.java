package com.sogeti.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {

	private static SessionFactory factory;

	private SessionFactoryBuilder() {

	}

	private static SessionFactory buildSessionFactory() {

		Configuration config = new Configuration();
		config.configure("hibernate-annotation.cfg.xml");

		factory = config.buildSessionFactory();

		return factory;
	}

	public static SessionFactory getSessionFactory() {

		if (factory == null) {
			factory = buildSessionFactory();
		}
		return factory;
	}
}
