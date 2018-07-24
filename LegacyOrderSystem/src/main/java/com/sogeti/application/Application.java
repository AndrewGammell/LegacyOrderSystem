package com.sogeti.application;

import com.sogeti.services.QueueService;

public class Application {

	public static void main(String[] argv) {

		QueueService queue = new QueueService();
		queue.run();
	}

}