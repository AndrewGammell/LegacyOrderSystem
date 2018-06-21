package com.sogeti.application;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import com.sogeti.command.Command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

public class Application {

	private static final Logger logger = Logger.getLogger(Application.class);
	private static final String RPC_QUEUE_NAME = "rpc_queue";
	private static final Gson gson = new Gson();
	private static Command command;

	public static void main(String[] argv) {
		logger.debug("QueueServer starting up");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

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
						// response = executeRequest(message);
						response = command.executeCommand();
					} catch (RuntimeException | SQLException e) {
						logger.error(e);
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
				synchronized (consumer) {
					try {
						consumer.wait();
					} catch (InterruptedException e) {
						logger.error(e);
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