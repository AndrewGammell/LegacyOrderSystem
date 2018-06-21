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
import com.sogeti.model.UserModel;

public class CreateCommandTestCases {

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
	public void testExecutePostValidOrderCommand() {
		command = new Command();
		command.setBody(getValidOrderJson());
		command.setType(Command.queryType.POST);
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
	public void testExecutePostInvalidOrderCommand() {
		command = new Command();
		command.setBody(getInvalidOrderJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePostNullOrderCommand() {
		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePostValidDetailCommand() throws SQLException {

		createOrderForTest();

		command = new Command();
		command.setBody(getValidDetailsJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {

			response = command.executeCommand();
			assertTrue(Boolean.valueOf(response));

			cleanDetailsTable();
			cleanOrderTable();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePostInvalidDetailCommand() {
		command = new Command();
		command.setBody(getInvalidDetailJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePostNullDetailCommand() {

		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePostValidUserCommand() throws SQLException {

		command = new Command();
		command.setBody(getValidUserJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {

			response = command.executeCommand();
			assertTrue(Boolean.valueOf(response));

			cleanUserTable();

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePostInvalidUserCommand() {
		command = new Command();
		command.setBody(getInvalidUserJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testExecutePostNullUserCommand() {

		command = new Command();
		command.setBody(NULL);
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);

		try {
			response = command.executeCommand();
			assertFalse(Boolean.valueOf(response));
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

	/////////////////////////////// SETUP ///////////////////////////

	private String getValidOrderJson() {

		OrderModel order = new OrderModel();
		order.setOrderId(ORDER_ID);
		order.setCreatedDate(new Date());
		order.setCreatedStaffId("999");
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(new Date());

		return gson.toJson(order);
	}

	private String getInvalidOrderJson() {

		OrderModel order = new OrderModel();
		order.setOrderId(1987);
		order.setCreatedDate(null);
		order.setCreatedStaffId("999");
		order.setStatus(OrderModel.Status.SHIPPED);
		order.setDateOrdered(null);

		return gson.toJson(order);
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

	private String getValidDetailsJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(12345);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId("987654321");
		detail.setCreatedDate(new Date());

		return gson.toJson(detail);
	}

	private String getInvalidDetailJson() {

		DetailModel detail = new DetailModel();
		detail.setOrderId(ORDER_ID);
		detail.setProductId(-172635);
		detail.setQuantity(20);
		detail.setUnitPrice(13);
		detail.setCreatedStaffId("987654321");
		detail.setCreatedDate(null);

		return gson.toJson(detail);
	}

	private void createOrderForTest() throws SQLException {

		command = new Command();
		command.setBody(getValidOrderJson());
		command.setType(Command.queryType.POST);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.executeCommand();

	}

	private void cleanDetailsTable() {

		Connection connector = RepositoryConnector.getConnection();
		Statement statement;

		try {
			statement = connector.createStatement();
			statement.execute(String.format(REMOVE_DETAIL_FROM_DB, ORDER_ID));
		} catch (SQLException sqle) {

		}
	}

	private String getValidUserJson() {

		UserModel user = new UserModel();
		user.setId(USER_ID);
		;
		user.setFirstName("John");
		user.setLastName("Doenut");
		user.setEmail("test@test.test");
		user.setPassword("secret");
		user.setDateOfBirth(new Date(01 / 01 / 1965));
		user.setCreatedDate(new Date());

		return gson.toJson(user);
	}

	private String getInvalidUserJson() {

		UserModel user = new UserModel();
		user.setId(USER_ID);
		;
		user.setFirstName(NULL);
		user.setLastName(NULL);
		user.setEmail(NULL);
		user.setPassword(NULL);
		user.setDateOfBirth(new Date(01 / 01 / 1965));
		user.setCreatedDate(new Date());

		return gson.toJson(user);
	}

	private void cleanUserTable() {

		Connection connector = RepositoryConnector.getConnection();
		Statement statement;

		try {
			statement = connector.createStatement();
			statement.execute(String.format(REMOVE_USER_FROM_DB, USER_ID));
		} catch (SQLException sqle) {

		}
	}

}
