package command.test.cases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.sogeti.command.Command;
import com.sogeti.model.OrderModel;

// TODO WRITE INVALID AND NULL TEST CASES
public class GetCommandTestCases {
	
	private Command command;
	private final int ORDER_ID = 123;
	private Gson gson = new Gson();

	@Test
	public void testExecuteGetOrderCommand() {
		try {
			String response = getOrderCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		}
	}

	@Test
	public void testExecuteGetAllOrdersCommand() {

		try {
			String response = getAllOrdersCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		}
	}

	@Test
	public void testExecuteGetDetailCommand() {

		try {
			String response = getDetailCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		}
	}

	@Test
	public void testExecuteGetAllDetailsCommand() {

		try {
			String response = getAllDetailsCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		}
	}

	@Test
	public void testExecuteGetuserCommand() {

		try {
			String response = getUserCommand().executeCommand();
			assertNotNull(response);
		} catch (SQLException e) {
			fail();
		}
	}

	@Test
	public void testExecuteGetOrderCommandInvlaidId() {

		try {
			getOrderCommandInvalidId().executeCommand();
			fail();
		} catch (SQLException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testExecutegetOrderCommandNullValue() {

		try {
			getOrderCommandNullValue().executeCommand();
			fail("No exception throw using invalid id");
		} catch (SQLException e) {
			assertTrue(true);
		} catch (NumberFormatException nfe) {
			assertTrue(true);
		}

	}

/////////////////////////////// SETUP ///////////////////////////
	
	private Command getOrderCommand() {

		Map<String, String> values = new HashMap<>();
		values.put("id", String.valueOf(ORDER_ID));

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		return command;
	}

	private Command getAllOrdersCommand() {

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setQuantity(Command.queryQuantity.MULTIPLE);
		command.setTable(Command.queryTable.ORDERS);

		return command;
	}

	private Command getDetailCommand() {

		Map<String, String> values = new HashMap<>();
		values.put("id", String.valueOf(ORDER_ID));

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		return command;
	}

	private Command getAllDetailsCommand() {

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.DETAILS);
		command.setQuantity(Command.queryQuantity.MULTIPLE);

		return command;
	}

	private Command getUserCommand() {

		Map<String, String> values = new HashMap<>();
		values.put("email", "tomselleck@test.com");
		values.put("password", "selleck");

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.USERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		return command;
	}

	private Command getOrderCommandInvalidId() {

		Map<String, String> values = new HashMap<>();
		values.put("id", "-1");

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);
		return command;
	}

	private Command getOrderCommandNullValue() {

		Map<String, String> values = new HashMap<>();
		values.put("id", null);

		Command command = new Command();
		command.setType(Command.queryType.GET);
		command.setTable(Command.queryTable.ORDERS);
		command.setQuantity(Command.queryQuantity.SINGLE);
		command.setValues(values);

		return command;
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

}