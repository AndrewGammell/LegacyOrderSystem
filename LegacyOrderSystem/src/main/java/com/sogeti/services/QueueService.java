package com.sogeti.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sogeti.command.Command;
import com.sogeti.properties.PropertiesReader;

public class QueueService implements Runnable {

	private final Logger		logger			= Logger.getLogger(this.getClass());
	private static final String	RPC_QUEUE_NAME	= PropertiesReader.getProperty("queue_name");
	private static final String	IP_ADDRESS		= PropertiesReader.getProperty("ip_address");
	private static final String	QUEUE_USERNAME	= PropertiesReader.getProperty("queue_username");
	private static final String	QUEUE_PASSWORD	= PropertiesReader.getProperty("queue_password");
	private static final String	QUEUE_PORT		= PropertiesReader.getProperty("queue_port");
	private static final Gson	gson			= new Gson();
	private static Command		command;

	@Override
	public void run() {
		logger.info("QueueServer starting up");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(Integer.valueOf(QUEUE_PORT));
		factory.setPassword(QUEUE_PASSWORD);
		factory.setUsername(QUEUE_USERNAME);
		logger.info("Queue port: " + factory.getPort());
		logger.info("Queue host: " + factory.getHost());
		logger.info("Queue password: " + factory.getPassword());
		logger.info("Queue virtualHost: " + factory.getVirtualHost());
		logger.info("Queue username: " + factory.getUsername());

		Connection connection = null;
		try {
			connection = factory.newConnection();
			final Channel channel = connection.createChannel();

			channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

			channel.basicQos(1);

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					logger.debug("message received");
					AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
							.correlationId(properties.getCorrelationId()).build();

					String response = "";

					try {
						String message = new String(body, "UTF-8");
						logger.info("command received: " + message);
						command = gson.fromJson(message, Command.class);
						response = command.executeCommand();
					} catch (RuntimeException | SQLException e) {
						logger.error(e);
						e.printStackTrace();
					} finally {
						channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
						channel.basicAck(envelope.getDeliveryTag(), false);
						// RabbitMq consumer worker thread notifies the RPC server owner thread
						synchronized (this) {
							this.notifyAll();
						}
					}
				}
			};

			channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
			// Wait and be prepared to consume the message from RPC client.
			while (true) {
				logger.debug("listening to the queue");
				synchronized (consumer) {
					try {
						consumer.wait();
					} catch (InterruptedException e) {
						logger.error(e);
						e.printStackTrace();
					}
				}
			}
		} catch (IOException | TimeoutException e) {
			logger.error(e);
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (IOException ioe) {
					logger.error(ioe);
				}
		}

	}

}
