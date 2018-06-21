package command.test.cases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.sogeti.command.Command;
import com.sogeti.connectors.RepositoryConnector;
import com.sogeti.model.DetailModel;
import com.sogeti.model.OrderModel;

public class UpdateCommandTestCase {

	private final String REMOVE_ORDER_FROM_DB = "DELETE FROM orders WHERE order_id=%s";
	private final String REMOVE_DETAIL_FROM_DB = "DELETE FROM order_details WHERE order_id=%s";
	private final String REMOVE_USER_FROM_DB = "DELETE FROM person WHERE id=%s";

	private Gson gson = new Gson();
	private Command command;
	private final String NULL = null;
	private final int ORDER_ID = 1987;
	private final int USER_ID = 123456789;
	private String response;

	@Test
	public void testExecutePutValidOrderCommand() throws SQLException {

		createOrderForTest();

		command = new Command();
		command.setBody(updateValidOrderJson());
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertTrue(Boolean.valueOf(response));

			cleanOrderTable();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePutInvalidOrderCommand() throws SQLException {

		createOrderForTest();

		command = new Command();
		command.setBody(updateInvalidOrderJson());
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));

			cleanOrderTable();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePutNullOrderCommand() throws SQLException {

		createOrderForTest();

		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));

			cleanOrderTable();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePutValidDetailCommand() throws SQLException {

		createOrderForTest();
		createDetailForTest();

		command = new Command();
		command.setBody(updateValidDetailsJson());
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertTrue(Boolean.valueOf(response));

			cleanDetailTable();
			cleanOrderTable();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePutInvalidDetailCommand() throws SQLException {

		createOrderForTest();

		command = new Command();
		command.setBody(updateInvalidDetailJson());
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));

			cleanOrderTable();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePutNullDetailsCommand() throws SQLException {

		createOrderForTest();

		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.PUT);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));

			cleanOrderTable();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	///////////////////////////// SETUP////////////////////////////

	private String updateValidOrderJson() {

		OrderModel order = new OrderModel();
		order.setOrderId(ORDER_ID);
		order.setCreatedDate(new Date());
		order.setCreatedStaffId("999");
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(new Date());
		order.setUpdatedDate(new Date());
		order.setUpdatedStaffId("12345");

		return gson.toJson(order);
	}

	private String updateInvalidOrderJson() {

		OrderModel order = new OrderModel();
		order.setOrderId(1987);
		order.setCreatedDate(null);
		order.setCreatedStaffId("999");
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(null);
		order.setUpdatedDate(new Date());
		order.setUpdatedStaffId("12345");

		return gson.toJson(order);
	}

	private String orderJson() {

		OrderModel order = new OrderModel();
		order.setOrderId(ORDER_ID);
		order.setCreatedDate(new Date());
		order.setCreatedStaffId("999");
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(new Date());

		return gson.toJson(order);
	}

	private void createOrderForTest() throws SQLException {

		command = new Command();
		command.setBody(orderJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.executeCommand();

	}

	private void cleanOrderTable() {

		Connection connector = RepositoryConnector.getConnection();
		Statement statement;

		try {
			statement = connector.createStatement();
			statement.execute(String.format(REMOVE_ORDER_FROM_DB, ORDER_ID));
		} catch (SQLException sqle) {

		}
	}

	private String updateValidDetailsJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(12345);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId("987654321");
		detail.setCreatedDate(new Date());
		detail.setUpdatedDate(new Date());
		detail.setUpdatedStaffId("56789");

		return gson.toJson(detail);
	}

	private String updateInvalidDetailJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(-172635);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId("987654321");
		detail.setCreatedDate(null);
		detail.setUpdatedDate(new Date());
		detail.setUpdatedStaffId("56789");

		return gson.toJson(detail);
	}

	private String detailsJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(12345);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId("987654321");
		detail.setCreatedDate(new Date());

		return gson.toJson(detail);
	}

	private void createDetailForTest() throws SQLException {

		command = new Command();
		command.setBody(detailsJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.executeCommand();

	}

	private void cleanDetailTable() {

		Connection connector = RepositoryConnector.getConnection();
		Statement statement;

		try {
			statement = connector.createStatement();
			statement.execute(String.format(REMOVE_DETAIL_FROM_DB, ORDER_ID));
		} catch (SQLException sqle) {

		}
	}
}
